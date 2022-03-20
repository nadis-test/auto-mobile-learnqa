package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;

public class SearchPageObject extends MainPageObject {
    private static final String
            SEARCH_INIT_ELEMENT = "xpath://*[@resource-id='org.wikipedia:id/search_container']//descendant::android.widget.TextView",
            SEARCH_INPUT = "xpath://*[@resource-id='org.wikipedia:id/search_src_text']",
            ONBORDING_BUTTON_SKIP = "xpath://*[contains(@text,'SKIP')]",
            SEARCH_RESULT_DESCRIPTION_BY_SUBSTRING_TPL = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_description'][contains(@text,'{SUBSTRING}')]",
            SEARCH_RESULT_TITLE_BY_SUBSTRING_TPL = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_title'][contains(@text,'{SUBSTRING}')]",
            SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_title'][contains(@text,'{TITLE}')]//following::android.widget.TextView[@resource-id = 'org.wikipedia:id/page_list_item_description'][contains(@text,'{DESCRIPTION}')]",
            SEARCH_CANCEL_BUTTON = "id:org.wikipedia:id/search_close_btn",
            SEARCH_RESULT_ELEMENT = "xpath://*[@resource-id='org.wikipedia:id/search_results_list']//*[@resource-id='org.wikipedia:id/page_list_item_title']",
            EMPTY_RESULT_ELEMENT = "xpath://*[@resource-id = 'org.wikipedia:id/results_text'][@text = 'No results']",
            SEARCH_RESULT_ELEMENT_BY_INSTANCE_TPL = "xpath://*[@class='android.view.ViewGroup'][@instance = {INSTANCE_NUMBER}]//descendant::android.widget.TextView[@resource-id = 'org.wikipedia:id/page_list_item_title']";;

    public SearchPageObject(AppiumDriver driver){
        super(driver);
    }

    // TEMPLATE METHODS section
    private static String getResultSearchElementByDescription(String substring) {
        return SEARCH_RESULT_DESCRIPTION_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }
    private static String getResultSearchElementByTitle(String substring) {
        return SEARCH_RESULT_TITLE_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }
    private static String getResultSearchElementByInstance(int instance_number){
        return SEARCH_RESULT_ELEMENT_BY_INSTANCE_TPL.replace("{INSTANCE_NUMBER}", String.valueOf(instance_number));
    }
    private static String getResultSearchElementByTitleAndDescription(String title, String description) {
        String replace_title = SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL.replace("{TITLE}", title);
        String replace_title_description = replace_title.replace("{DESCRIPTION}", description);
        System.out.println("getResultSearchElementByTitleAndDescription = " + replace_title_description);
        return replace_title_description;
    }
    // TEMPLATE METHODS section

    public void skipOnboarding(){
        this.waitForElementAndClick(ONBORDING_BUTTON_SKIP, "Cannot find and click search init element", 5);
    }

    public void initSearchInput() {
        this.waitForElementPresent(SEARCH_INIT_ELEMENT, "Cannot find search input before clicking search init element", 5);
        this.waitForElementAndClick(SEARCH_INIT_ELEMENT, "Cannot find and click search init element", 5);
    }

    public void typeSearchLine(String search_line){
        this.waitForElementAndSendKeys(SEARCH_INPUT, search_line, "Cannot find and send keys to search input", 5);
    }

    public void waitForSearchResultByDescription(String substring){
        String search_result_xpath = getResultSearchElementByDescription(substring);
        this.waitForElementPresent(search_result_xpath, "Cannot find search result with substring + '" + substring + "'", 5);
    }

    public void waitForSearchResultByInstance(int instance_number){
        String search_result_xpath = getResultSearchElementByInstance(instance_number);
        this.waitForElementPresent(
                search_result_xpath,
                instance_number + " article wasn't found on search result page",
                15);
    }

    public void assertSearchResultByInstanceContainsQuery(int instance_number, String search_query){
        String search_result_element_xpath = getResultSearchElementByInstance(instance_number);
        this.assertElementHasText(
                search_result_element_xpath,
                search_query,
                "'Text' attribute for the " + instance_number + " article title doesn't match with expected '" + search_query + "' value",
                5);
    }

    public void clickByArticleWithDescription(String substring){
        String search_result_xpath = getResultSearchElementByDescription(substring);
        this.waitForElementAndClick(search_result_xpath, "Cannot find and click search result with substring + '" + substring + "'", 10);
    }

    public void clickByArticleWithTitle(String substring){
        String search_result_xpath = getResultSearchElementByTitle(substring);
        this.waitForElementAndClick(search_result_xpath, "Cannot find and click search result with substring + '" + substring + "'", 10);
    }

    public void waitForCancelButtonToAppear(){
        this.waitForElementPresent(SEARCH_CANCEL_BUTTON, "Cannot find search cancel button", 5);
    }

    public void waitForCancelButtonToDisappear(){
        this.waitForElementNotPresent(SEARCH_CANCEL_BUTTON, "Search cancel button is still present", 5);
    }

    public void clickCancelSearch(){
        this.waitForElementAndClick(SEARCH_CANCEL_BUTTON, "Cannot click search cancel button", 5);
    }

    public int getAmountOfFoundArticles(){
        this.waitForElementPresent(SEARCH_RESULT_ELEMENT,
                "Cannot find any results by search request",
                15);
        return this.getAmountOfElements(SEARCH_RESULT_ELEMENT);
    }

    public void waitForEmptyResultLabel(){
        this.waitForElementPresent(EMPTY_RESULT_ELEMENT,
                "Cannot find empty search results label",
                15);
    }

    public void assertThereIsNoSearchResult(){
        this.assertElementNotPresent(SEARCH_RESULT_ELEMENT,
                "Search results are not empty");
    }

    public void checkSearchInputCaption(String search_input_caption){
        this.assertElementHasText(
                SEARCH_INIT_ELEMENT,
                search_input_caption,
                "'Text' attribute for the search field doesn't match with expected value: '" + search_input_caption + "'",
                5);
    }

    public WebElement getSearchResultElementByInstance(int instance_number){
        String search_result_element_xpath = getResultSearchElementByInstance(instance_number);
        return this.waitForElementPresent(
                search_result_element_xpath,
                "No search result elements present",
                15);
    }

    public void waitForElementByTitleAndDescription(String title, String description){
        String search_result_xpath = getResultSearchElementByTitleAndDescription(title, description);
        this.waitForElementPresent(
                search_result_xpath,
                "Cannot find search result with title + '" + title + "' and description '" + description +"'",
                5);
    }
}

