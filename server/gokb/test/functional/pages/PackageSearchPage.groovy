package pages

class PackageSearchPage extends BaseSearchPage {

    // static url = "/gokb"
    static url = "/gokbLabs/search/index?qbe=g%3A1packages";

    static at = { 
      browser.page.title.startsWith "Search" 
    };


    // Find useful links and make them available to spec objects
    static content = {
        searchButton {
             $("button", name: 'searchAction').click()
         }
        
         searchFor { searchText ->
           $('input', name:'qp_name').value(searchText)
           $("button", name: 'searchAction').click()
         }
    }
}
