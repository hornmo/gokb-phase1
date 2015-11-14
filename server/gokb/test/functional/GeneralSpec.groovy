import geb.error.RequiredPageContentNotPresent
import geb.spock.GebReportingSpec
import pages.*
import spock.lang.Stepwise
import spock.lang.Ignore

@Stepwise
class GeneralSpec extends BaseSpec {

  def "Create organisation" (){
    setup:
      to FrontPage
      loginLink()
      // at LogInPage
      // login(Data.UserD_name, Data.UserD_passwd)
    when:
      go "/gokbLabs"
      // $("form").name = Data.Org_name
      // $("form").impId = Data.Org_impId
      // $("form").sector = "Higher Education"
      // report "google home page"
      // $("#SubmitButton").click()
    then:
      browser.page.title.startsWith "GOKb: Welcome"
  }

}
