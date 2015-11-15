import geb.error.RequiredPageContentNotPresent
import geb.spock.GebReportingSpec
import pages.*
import spock.lang.Stepwise
import spock.lang.Ignore

@Stepwise
class GeneralSpec extends BaseSpec {

   // see http://www.adavis.info/2014/04/grails-functional-testing-with-geb-and.html
   // see https://grails.org/plugin/functional-test

  def "Test Front Page" (){
    setup:
      to FrontPage
      report "GoKB front page"
      loginLink()
      report "GoKB login page"
      at LogInPage
      login(Data.admin_username, Data.admin_password)
    when:
      go "/gokbLabs"
      // $("form").name = Data.Org_name
      // $("form").impId = Data.Org_impId
      // $("form").sector = "Higher Education"
      // report "google home page"
      // $("#SubmitButton").click()
    then:
      browser.page.title.startsWith 'GOKb: Dashboard'
  }

  def "Create a new source for CUP"() {
    setup:
      to CreateSource
    when:
      waitElement {$('#\\:shortcode').click()}
      $('form.editableform div.editable-input textarea.form-control').value('AutomatedTestSource1')
      $('form.editableform button.editable-submit').click()
      waitElement {$('#\\:name').click()}
      $('form.editableform div.editable-input textarea.form-control').value('Automated Test Source One')
      $('form.editableform button.editable-submit').click()
      $('#save-btn').click()
    then:
      // Wait a second for the JS to get the response code back and redirect us to the right place
      synchronized(this) {
        Thread.sleep(2000)
      }
      browser.page.title.startsWith 'GOKb: Source'
  }

  def "Load CUP all titles file"() {
    setup:
      to EbooksUpload
    when:
      $('#NewIngestProfileButton').click()
      $('Form').profileName='CUP Master File'
      $('Form').packageName='CUP Master File'
      $('Form').platformUrl='http://www.cup.ac.uk'
      // Click the format dropdown - and select kbart2
      $('#s2id_autogen3').click()
      $('#select2-result-label-42').click()
      // Click the source dropdown - and select CUP
      $('#s2id_autogen5').click()
      $('#select2-result-label-36').click()
      $('#submissionFile').value('///some/file')
    then:
      1==1
  }

}
