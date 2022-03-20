package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import org.openqa.selenium.By;

public class WelcomePageObject extends MainPageObject{

    private static final String
        STEP_LEARN_MORE_LINK = "-ios class chain:**/XCUIElementTypeStaticText[`label == \"Learn more about Wikipedia\"`]",
        STEP_NEW_WAYS_TO_EXPLORE = "-ios class chain:**/XCUIElementTypeStaticText[`label == \"New ways to explore\"`]",
        STEP_ADD_AND_EDIT_PREF_LANG = "-ios class chain:**/XCUIElementTypeStaticText[`label == \"Add or edit preferred languages\"`]",
        STEP_LEARN_MORE_ABOUT_DATA_COLLECTED = "-ios class chain:**/XCUIElementTypeStaticText[`label == \"Learn more about data collected\"`]",
        NEXT_BUTTON = "-ios class chain:**/XCUIElementTypeStaticText[`label == \"Next\"`]",
        GET_STARTED_BUTTON = "-ios class chain:**/XCUIElementTypeStaticText[`label == \"Get started\"`]";


    public WelcomePageObject(AppiumDriver driver){
        super(driver);
    }

   public void waitForMoreLearnLink(){
        this.waitForElementPresent(STEP_LEARN_MORE_LINK,"'Learn more about Wikipedia' link not found", 10);
   }

    public void waitForNewWaysToExplore(){
        this.waitForElementPresent(STEP_NEW_WAYS_TO_EXPLORE,"'New ways to explore' title not found", 10);
    }

    public void waitForAddAndEditPreferredLangText(){
        this.waitForElementPresent(STEP_ADD_AND_EDIT_PREF_LANG,"'Add or edit preferred languages' link not found", 10);
    }

    public void waitForLearnMoreAboutDataCollectedText(){
        this.waitForElementPresent(STEP_LEARN_MORE_ABOUT_DATA_COLLECTED,"'Learn more about data collected' link not found", 10);
    }

   public void clickNextButton(){
       this.waitForElementAndClick(NEXT_BUTTON, "Next button not found", 5);
   }

    public void clickGetStartedButton(){
        this.waitForElementAndClick(GET_STARTED_BUTTON, "'Get started' button not found", 5);
    }
}
