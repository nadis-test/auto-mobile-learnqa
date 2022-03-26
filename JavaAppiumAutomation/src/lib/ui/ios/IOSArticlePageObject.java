package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.ArticlePageObject;

public class IOSArticlePageObject extends ArticlePageObject {
    static {
        TITLE = "-ios class chain:**/XCUIElementTypeStaticText[`label == 'Object-oriented programming language'`]";
        FOOTER_ELEMENT = "id:View article in browser";
        SAVE_TO_MY_LIST_BUTTON = "-ios class chain:**/XCUIElementTypeButton[`label == 'Save for later'`]";
        CLOSE_ARTICLE_BUTTON = "id:Back";
    }
    public IOSArticlePageObject(AppiumDriver driver){
        super(driver);
    }
}
