package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.ArticlePageObject;

public class AndroidArticlePageObject extends ArticlePageObject {
    static {
                TITLE = "id:pcs-edit-section-title-description";
                FOOTER_ELEMENT = "xpath://*[@text='View article in browser']";
                SAVE_TO_MY_LIST_BUTTON = "xpath://*[@resource-id='org.wikipedia:id/article_menu_bookmark']";
                OPTIONS_ADD_TO_MY_LIST_BUTTON = "xpath://*[contains(@text,'ADD TO LIST')]";
                NEW_LIST_NAME_INPUT = "xpath://*[@resource-id='org.wikipedia:id/text_input']";
                MY_LIST_OK_BUTTON = "xpath://*[@resource-id='android:id/button1'][@text = 'OK']";
                EXISTING_LIST_TITLE_TPL = "xpath://*[@resource-id ='org.wikipedia:id/item_title'][@text = '{LIST_TITLE}']";
                CLOSE_ARTICLE_BUTTON = "xpath://*[contains(@content-desc,'Navigate up')]";
    }
    public AndroidArticlePageObject(AppiumDriver driver){
        super(driver);
    }
}
