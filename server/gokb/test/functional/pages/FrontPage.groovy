package pages
/**
 * Created by ioannis on 28/05/2014.
 */
class FrontPage extends BasePage {
    static url = "/gokbLabs"
    static at = { browser.page.title.startsWith "GOKb: Welcome" };

    static content = {
        loginLink {
            waitFor { $("a", text: "Sign in")}
             $("a", text: "Sign in").click()
        }

    }
}

