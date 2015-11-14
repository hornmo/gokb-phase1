package pages

class LogInPage extends BasePage {
    static url = "/gokbLabs/login/auth"
    static at = { browser.page.title.startsWith "Login" };

    static content = {
        login { name, passwd ->
            $("form").j_username = name
            $("form").j_password = passwd
            $("#submit").click()
        }
    }
}

