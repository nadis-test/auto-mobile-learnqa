package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.WelcomePageObject;

public class IOSWelcomePageObject extends WelcomePageObject {
    static {
        STEP_LEARN_MORE_LINK = "-ios class chain:**/XCUIElementTypeStaticText[`label == 'Learn more about Wikipedia'`]";
                STEP_NEW_WAYS_TO_EXPLORE = "-ios class chain:**/XCUIElementTypeStaticText[`label == 'New ways to explore'`]";
                STEP_ADD_AND_EDIT_PREF_LANG = "-ios class chain:**/XCUIElementTypeStaticText[`label == 'Add or edit preferred languages'`]";
                STEP_LEARN_MORE_ABOUT_DATA_COLLECTED = "-ios class chain:**/XCUIElementTypeStaticText[`label == 'Learn more about data collected'`]";
                NEXT_BUTTON = "-ios class chain:**/XCUIElementTypeStaticText[`label == 'Next'`]";
                GET_STARTED_BUTTON = "-ios class chain:**/XCUIElementTypeStaticText[`label == 'Get started'`]";
                SKIP_BUTTON = "-ios class chain:**/XCUIElementTypeButton[`label == 'Skip'`]";
    }

    public IOSWelcomePageObject(AppiumDriver driver){
        super(driver);
    }
}
