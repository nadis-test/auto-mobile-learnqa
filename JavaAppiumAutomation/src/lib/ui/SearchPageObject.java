package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class SearchPageObject extends MainPageObject {
    private static final String
            SEARCH_INIT_ELEMENT = "//*[contains(@text,'Search Wikipedia')]",
            SEARCH_INPUT = "//*[@resource-id='org.wikipedia:id/search_src_text']",
            ONBORDING_BUTTON_SKIP = "//*[contains(@text,'SKIP')]",
            SEARCH_RESULT_BY_SUBSTRING_TPL = "//*[@resource-id='org.wikipedia:id/page_list_item_description'][contains(@text,'{SUBSTRING}')]",
            SEARCH_CANCEL_BUTTON = "org.wikipedia:id/search_close_btn",
            SEARCH_RESULT_ELEMENT = "//*[@resource-id='org.wikipedia:id/search_results_list']//*[@resource-id='org.wikipedia:id/page_list_item_title']",
            EMPTY_RESULT_ELEMENT = "//*[@resource-id = 'org.wikipedia:id/results_text'][@text = 'No results']";

    public SearchPageObject(AppiumDriver driver){
        super(driver);
    }

    // TEMPLATE METHODS section
    private static String getResultSearchElement(String substring){
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }
    // TEMPLATE METHODS section

    public void skipOnboarding(){
        this.waitForElementAndClick(By.xpath(ONBORDING_BUTTON_SKIP), "Cannot find and click search init element", 5);
    }

    public void initSearchInput() {
        this.waitForElementPresent(By.xpath(SEARCH_INIT_ELEMENT), "Cannot find search input before clicking search init element", 5);
        this.waitForElementAndClick(By.xpath(SEARCH_INIT_ELEMENT), "Cannot find and click search init element", 5);
    }

    public void typeSearchLine(String search_line){
        this.waitForElementAndSendKeys(By.xpath(SEARCH_INPUT), search_line, "Cannot find and send keys to search input", 5);
    }

    public void waitForSearchResult(String substring){
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementPresent(By.xpath(search_result_xpath), "Cannot find search result with substring + '" + substring + "'", 5);
    }

    public void clickByArticleWithSubstring(String substring){
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(By.xpath(search_result_xpath), "Cannot find and click search result with substring + '" + substring + "'", 10);
    }

    public void waitForCancelButtonToAppear(){
        this.waitForElementPresent(By.id(SEARCH_CANCEL_BUTTON), "Cannot find search cancel button", 5);
    }

    public void waitForCancelButtonToDisappear(){
        this.waitForElementNotPresent(By.id(SEARCH_CANCEL_BUTTON), "Search cancel button is still present", 5);
    }

    public void clickCancelSearch(){
        this.waitForElementAndClick(By.id(SEARCH_CANCEL_BUTTON), "Cannot click search cancel button", 5);
    }

    public int getAmountOfFoundArticles(){
        this.waitForElementPresent(By.xpath(SEARCH_RESULT_ELEMENT),
                "Cannot find any results by search request",
                15);
        return this.getAmountOfElements(By.xpath(SEARCH_RESULT_ELEMENT));
    }

    public void waitForEmptyResultLabel(){
        this.waitForElementPresent(By.xpath(EMPTY_RESULT_ELEMENT),
                "Cannot find empty search results label",
                15);
    }

    public void assertThereIsNoSearchResult(){
        this.assertElementNotPresent(By.xpath(SEARCH_RESULT_ELEMENT),
                "Search results are not empty");
    }
}

