package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.MyListsPageObject;

public class IOSMyListPageObject extends MyListsPageObject {
    static {
        ARTICLE_BY_TITLE_TPL = "-ios class chain:**/XCUIElementTypeStaticText[`label == '{ARTICLE_TITLE}'`]";
        IOS_DELETE_BUTTON = "id:swipe action delete";
        CLOSE_SYNC_OVERLAY_BUTTON = "id:Close";
    }
    public IOSMyListPageObject(AppiumDriver driver){
        super(driver);
    }
}
