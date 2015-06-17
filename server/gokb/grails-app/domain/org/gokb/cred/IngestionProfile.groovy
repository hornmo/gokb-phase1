package org.gokb.cred

import javax.persistence.Transient;

class IngestionProfile extends KBComponent {
	String packageName
	RefdataValue packageType
	String platformUrl
	
	static manyByCombo = [
		datafiles: DataFile
	]
	
	static hasByCombo = [
		source: Source	
	]
	
	static constraints = {
		packageName (nullable:false, blank:false)
		packageType (nullable:false, blank:false)
		platformUrl (nullable:true, blank:false)	
	}
	
	static mapping = {
		packageName column:'ebdf_packageName'
		packageType column:'ebdf_packageType'
		platformUrl column:'endf_default_platform_url'
	}

	public String getNiceName() {
		return "Ingestion Profile"
	}

	 	  
	  @Transient
	  def getMissingTipps() {
		  def result=[]
		  if (datafiles && datafiles.size()>1) {
			  Collections.sort(datafiles, {a, b -> a.dateCreated <=> b.dateCreated} as Comparator)
			  result=datafiles[datafiles.size()-2].tipps
			  result-=datafiles.last().tipps
		  }
		  result
	  }
	  
	  @Transient
	  def getNewTipps() {
		  def result=[]
		  if (datafiles) {
			  Collections.sort(datafiles, {a, b -> a.dateCreated <=> b.dateCreated} as Comparator)
			  result=datafiles.last().tipps
			  if (datafiles.size()>1) {
				  result-=datafiles[datafiles.size()-2].tipps
			  }
		  }
		  result
	  }

	  	/**
	 *  Override so that we only return EbookDataFiles that are editable on the
	 * typedown searches
	 */
	@Override
	static def refdataFind(params) {
	  def result = [];
	  def ql = null;
	  ql = IngestionProfile.findAllByNameIlike("${params.q}%",params)
	  if ( ql ) {
		ql.each { t ->
		  result.add([id:"${t.class.name}:${t.id}",text:"${t.name}"])
		}
	  }
  
	  result
	}
}
