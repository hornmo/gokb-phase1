package org.gokb

import com.k_int.ConcurrencyManagerService
import com.k_int.ConcurrencyManagerService.Job
import grails.plugins.springsecurity.Secured
import java.security.MessageDigest
import grails.converters.*
import org.gokb.cred.*

class EBookUploadController {

  def TSVIngestionService
  def concurrencyManagerService
  def genericOIDService
  def sessionFactory
  def propertyInstanceMap = org.codehaus.groovy.grails.plugins.DomainClassGrailsPlugin.PROPERTY_INSTANCE_MAP

  
  @Secured(['ROLE_USER', 'IS_AUTHENTICATED_FULLY'])
  def index() {
  }
  
  @Secured(['ROLE_USER', 'IS_AUTHENTICATED_FULLY'])
  def forceIngest() {
    log.debug("in forced ingest")
    def result = genericOIDService.resolveOID(params.id)
    TSVIngestionService.ingest(result, result.datafiles.last())
    redirect(controller:'resource',action:'show',id:"${params.id}")
  }
  
  @Secured(['ROLE_USER', 'IS_AUTHENTICATED_FULLY'])
  def processSubmission() {
    log.debug(params)
    if ( request.method == 'POST' ) {
    def temp_file
    try {
      def ingestion_profile_id=request.getParameter("ingestionProfileId")
      def upload_mime_type = request.getFile("submissionFile")?.contentType
      def upload_filename = request.getFile("submissionFile")?.getOriginalFilename()
      def upload_packageType = genericOIDService.resolveOID(request.getParameter("packageType"))
      def upload_packageName = request.getParameter("packageName")
      def default_url = request.getParameter("platformUrl") //this is gonna go into source as well
      def profile_name = request.getParameter("profileName")
      def source_id=request.getParameter("sourceId")
      def source_name=request.getParameter("sourceName")
      def source_default_supply_method = request.getParameter("defaultSupplyMethod")
      def source_org_id = request.getParameter("orgId") //repsonsible person
      
      //do some simple validation:
      if (!ingestion_profile_id) {
        if (!profile_name || !default_url || !upload_packageName) {
          log.error("missing parameters to ebook upload - profile")
          log.error("profile name: ${profile_name}")
          log.error("default url: ${default_url}")
          log.error("package name: ${upload_packageName}")
          throw new Exception("missing ingestion profile")
        }
        if (!source_id) {
          if (!source_name || !source_default_supply_method || !source_org_id) {
            log.error("missing parameters to ebook upload - source")
            log.error("source name: ${source_name}")
            log.error("default supply method: ${source_default_supply_method}")
            log.error("source org id: ${source_org_id}")
            throw new Exception("missing source creation information")
          }
        }
      } 
      
      // store input stream locally
      def deposit_token = java.util.UUID.randomUUID().toString();
      if ( upload_filename ) {
        temp_file = copyUploadedFile(request.getFile("submissionFile"), deposit_token);
      }
      else if ( params.submissionURL ) {
        log.debug("Process submission URL ${params.submissionURL}");
        def submission_url = new java.net.URL(params.submissionURL)
        temp_file = copyUploadedURL(submission_url, deposit_token);
        upload_filename=params.submissionURL
        upload_mime_type=''
      }
      else {
        log.error("No file to process");
        return
      }

      def info = analyse(temp_file);
  
      log.debug("Got file with md5 ${info.md5sumHex}.. lookup");
  
      def existing_file = DataFile.findByMd5(info.md5sumHex);
  
      if ( existing_file != null ) {
      log.debug("Found a match !")
      redirect(controller:'resource',action:'show',id:"org.gokb.cred.DataFile:${existing_file.id}")
      }
      else {
      def ingestion_profile
      def source
        
      if (ingestion_profile_id) {
        ingestion_profile = genericOIDService.resolveOID(ingestion_profile_id)
        source=ingestion_profile.source
      } else {
          ingestion_profile = new IngestionProfile(name:profile_name,
                             source:source,
                             packageName:upload_packageName,
                              packageType:upload_packageType, 
                             platformUrl:default_url).save(flush:true)  
        log.debug("created a new ingestion profile: ${ingestion_profile}")
        if (source_id) {
          source=genericOIDService.resolveOID(source_id)
        } else {
          source = new Source(name:source_name,
                    url: default_url,
                    defaultSupplyMethod:genericOIDService.resolveOID(source_default_supply_method),
                    responsibleParty:genericOIDService.resolveOID(source_org_id)).save(flush:true)
          log.debug("created a new source: ${source}")
        }
      }

      def new_datafile = new DataFile(
                         guid:deposit_token,
                           md5:info.md5sumHex,
                           uploadName:upload_filename,
                           name:upload_filename,
                           filesize:info.filesize,
                           uploadMimeType:upload_mime_type).save(flush:true)  
      new_datafile.fileData = temp_file.getBytes()
      if (!ingestion_profile.ingestions) {
        ingestion_profile.ingestions=[]  
      }

      def new_ingestion_info = new ComponentIngestionSource(profile:ingestion_profile, component:new_datafile)
      ingestion_profile.ingestions << new_ingestion_info

      new_datafile.save(flush:true)
      ingestion_profile.save(flush:true)
      log.debug("Saved file on database ")



      Job background_job = concurrencyManagerService.createJob { Job job ->
        // Create a new session to run the ingest.
        TSVIngestionService.ingest(ingestion_profile, new_datafile, job)
        log.debug ("Async Data insert complete")
      }
      background_job.startOrQueue()

      // Release all objects so we don't get any clashes
      cleanUpGorm();

      // Wait
      log.debug("Ingest mode: ${params.ingestMode}");
      if ( params.ingestMode=='background' ) {
      }
      else {
        log.debug("Foreground mode.. waiting");
        background_job.get();
      }

      redirect(controller:'resource',action:'show',id:"org.gokb.cred.IngestionProfile:${ingestion_profile.id}")
      }
    }
    catch ( Exception e ) {
      log.error("Problem processing uploaded file",e);
    } finally{
      temp_file?.delete()
    }
    }    
    render(view:'index')
  }

  def createTempFile(deposit_token) {
   def baseUploadDir = grailsApplication.config.baseUploadDir ?: '.'
    log.debug("copyUploadedFile...");
    def sub1 = deposit_token.substring(0,2);
    def sub2 = deposit_token.substring(2,4);
    validateUploadDir("${baseUploadDir}");
    validateUploadDir("${baseUploadDir}/${sub1}");
    validateUploadDir("${baseUploadDir}/${sub1}/${sub2}");
    def temp_file_name = "${baseUploadDir}/${sub1}/${sub2}/${deposit_token}";
    def temp_file = new File(temp_file_name);
    temp_file
  }
  
  def copyUploadedURL(url, deposit_token) {
    def temp_file = createTempFile(deposit_token)
    org.apache.commons.io.FileUtils.copyURLToFile(url,temp_file)
    temp_file
  }

  def copyUploadedFile(inputfile, deposit_token) {
    def temp_file = createTempFile(deposit_token)
    // Copy the upload file to a temporary space
    inputfile.transferTo(temp_file);
    temp_file
  }
    
  private def validateUploadDir(path) {
    File f = new File(path);
    if ( ! f.exists() ) {
      log.debug("Creating upload directory path")
      f.mkdirs();
    }
  }
    
  def analyse(temp_file) {
    
    def result=[:]
    result.filesize = 0;
    
    log.debug("analyze...");
    
    // Create a checksum for the file..
    MessageDigest md5_digest = MessageDigest.getInstance("MD5");
    InputStream md5_is = new FileInputStream(temp_file);
    byte[] md5_buffer = new byte[8192];
    int md5_read = 0;
    while( (md5_read = md5_is.read(md5_buffer)) >= 0) {
      md5_digest.update(md5_buffer, 0, md5_read);
      result.filesize += md5_read
    }
    md5_is.close();
    byte[] md5sum = md5_digest.digest();
    result.md5sumHex = new BigInteger(1, md5sum).toString(16);
    
    log.debug("MD5 is ${result.md5sumHex}");
    result
  }
  
  def cleanUpGorm() {
    log.debug("Clean up GORM");

    // Get the current session.
    def session = sessionFactory.currentSession

    // flush and clear the session.
    session.flush()
    session.clear()

    // Clear the property instance map.
    propertyInstanceMap.get().clear()
  }

}
