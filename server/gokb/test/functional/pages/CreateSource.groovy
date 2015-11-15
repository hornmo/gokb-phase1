package pages
/**
 * Created by ioannis on 28/05/2014.
 */
class CreateSource extends BasePage {
    static url = "/gokbLabs/create/index?tmpl=org.gokb.cred.Source"
    static at = { browser.page.title.startsWith "GOKb: Create New Source" };

}
