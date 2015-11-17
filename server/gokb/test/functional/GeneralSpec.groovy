import geb.error.RequiredPageContentNotPresent
import geb.spock.GebReportingSpec
import pages.*
import spock.lang.Stepwise
import spock.lang.Ignore

@Stepwise
class GeneralSpec extends BaseSpec {

   // see http://www.adavis.info/2014/04/grails-functional-testing-with-geb-and.html
   // see https://grails.org/plugin/functional-test

  def "Test Front Page and Login" (){
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
    when: "I specify a new souce"
      // println("Some output from the test spec");
      waitElement {$('#\\:shortcode').click()}
      $('form.editableform div.editable-input textarea.form-control').value('AutomatedTestSource1')
      $('form.editableform button.editable-submit').click()
      waitElement {$('#\\:name').click()}
      $('form.editableform div.editable-input textarea.form-control').value('Automated Test Source One')
      $('form.editableform button.editable-submit').click()
      $('#save-btn').click()
    then: "A new source should be created in the database, and the new source should be shown in the browser"
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
      synchronized(this) { Thread.sleep(500) }
      report "Click format"
      $('#select2-result-label-12').click()
      // Click the source dropdown - and select CUP
      $('#s2id_autogen5').click()
      report "Selected KBART"
      synchronized(this) { Thread.sleep(500) }

      report "Click source"
      $('#select2-result-label-17').click()
      report "Selected CUP"

      // see http://www.gebish.org/manual/current/#select
      $('#ingestModeSelect').value('foreground');

      $('#submissionURL').value('https://github.com/k-int/gokb-phase1/raw/labs/testdata/ebooks/journals.cambridge.org_AllTitles_2015-07-14.txt');
      report "Set file"

      def start = System.currentTimeMillis();
      // Load https://github.com/k-int/gokb-phase1/raw/labs/testdata/ebooks/YBP1_1.tsv
      $('#submit-url').click();

    then:
      synchronized(this) { Thread.sleep(500) }
      def elapsed = System.currentTimeMillis() - start
      def a = report "Ingest of CUP completed"
      println "Ingest of CUP Master list completed in ${elapsed}ms == ${elapsed/1000}s";
      println('Image at ../../geb-reports/GeneralSpec/003-006-Load%20CUP%20all%20titles%20file-Ingest%20of%20CUP%20completed.png');
      browser.page.title.startsWith 'GOKb: Ingestion Profile'

      // Select the ingestions tab
      $('a', 'href':'#ingestions').click()
  }

  def "Search for 'CUP Master File' returns one package"() {
    setup:
      to PackageSearchPage
    when:
      searchFor('CUP Master File')
    then:
      def total = $('#search-result-total-records').text()?.trim()
      println("search for CUP master file returned ${total} hit");
      total=='1'
  }

  def "Search for Acta Numerica returns one title"() {
    setup:
      to TitleSearchPage
    when:
      searchFor('Acta Numerica')
    then:
      def total = $('#search-result-total-records').text()?.trim()
  }

}
