package lib.ui;

import io.appium.java_client.AppiumDriver;

public class WelcomePageObject extends MainPageObject{

    protected static String
        STEP_LEARN_MORE_LINK,
        STEP_NEW_WAYS_TO_EXPLORE,
        STEP_ADD_AND_EDIT_PREF_LANG,
        STEP_LEARN_MORE_ABOUT_DATA_COLLECTED,
        NEXT_BUTTON,
        GET_STARTED_BUTTON,
        SKIP_BUTTON;


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

    public void clickSkip(){
        this.waitForElementAndClick(SKIP_BUTTON, "Skip button not found", 5);
    }
}
