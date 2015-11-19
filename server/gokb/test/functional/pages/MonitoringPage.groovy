package pages

class TitleSearchPage extends BaseSearchPage {

    // static url = "/gokb"
    static url = "/gokbLabs/monitoring";

    static at = { 
      browser.page.title.startsWith "Search" 
    };


    // Find useful links and make them available to spec objects
    static content = {
    }
}
