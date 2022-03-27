package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.WelcomePageObject;

public class AndroidWelcomePageObject extends WelcomePageObject {
    static {
        SKIP_BUTTON = "xpath://*[contains(@text,'SKIP')]";
    }
    public AndroidWelcomePageObject(AppiumDriver driver){
        super(driver);
    }
}
