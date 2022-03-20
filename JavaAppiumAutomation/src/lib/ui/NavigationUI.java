package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class NavigationUI extends MainPageObject{
    private static final String
            NAVIGATE_TO_MAIN_PAGE_BUTTON = "xpath://*[@resource-id = 'org.wikipedia:id/search_toolbar']//*[@class = 'android.widget.ImageButton']",
            SAVED_LISTS_BUTTON = "xpath://*[contains(@content-desc, 'Saved')]";


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
