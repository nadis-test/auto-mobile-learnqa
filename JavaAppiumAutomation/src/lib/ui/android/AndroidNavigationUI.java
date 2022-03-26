package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.NavigationUI;

public class AndroidNavigationUI extends NavigationUI {
    static {
        NAVIGATE_TO_MAIN_PAGE_BUTTON = "xpath://*[@resource-id = 'org.wikipedia:id/search_toolbar']//*[@class = 'android.widget.ImageButton']";
        SAVED_LISTS_BUTTON = "xpath://*[contains(@content-desc, 'Saved')]";
    }

    public AndroidNavigationUI(AppiumDriver driver){
        super(driver);
    }
}
