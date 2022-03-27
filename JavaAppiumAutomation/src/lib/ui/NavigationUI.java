package lib.ui;

import io.appium.java_client.AppiumDriver;

abstract public class NavigationUI extends MainPageObject{
    protected static String
            NAVIGATE_TO_MAIN_PAGE_BUTTON,
            SAVED_LISTS_BUTTON;

    public NavigationUI(AppiumDriver driver){
        super(driver);
    }

    public void returnFromSearchResultsToMainPage(){
        //возвращаюсь со страницы результатов поиска на главную страницу
            this.waitForElementAndClick(NAVIGATE_TO_MAIN_PAGE_BUTTON,
                    "'Navigate up' from search results page to main page not found on toolbar",
                    5);
    }

    public void clickSavedLists(){
        //кликаю на кнопку сохраненного на нав панели
        this.waitForElementAndClick(SAVED_LISTS_BUTTON,
                "Saved button not found on navigation panel",
                5);
    }
}
