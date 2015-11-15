package pages
/**
 * Created by ioannis on 28/05/2014.
 */
class EbooksUpload extends BasePage {
    static url = 'gokbLabs/EBookUpload/index'
    static at = { browser.page.title.startsWith "GOKb: eBook File Upload" };

    static content = {
    }
}
