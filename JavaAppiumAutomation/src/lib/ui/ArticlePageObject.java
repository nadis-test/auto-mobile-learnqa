package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject {
    private static final String
    TITLE = "pcs-edit-section-title-description",
    FOOTER_ELEMENT = "//*[@text='View article in browser']",
    SAVE_TO_MY_LIST_BUTTON = "//*[@resource-id='org.wikipedia:id/article_menu_bookmark']",
    OPTIONS_ADD_TO_MY_LIST_BUTTON = "//*[contains(@text,'ADD TO LIST')]",
    NEW_LIST_NAME_INPUT = "//*[@resource-id='org.wikipedia:id/text_input']",
    MY_LIST_OK_BUTTON = "//*[@resource-id='android:id/button1'][@text = 'OK']",
    EXISTING_LIST_TITLE_TPL = "//*[@resource-id ='org.wikipedia:id/item_title'][@text = '{LIST_TITLE}']",
    CLOSE_ARTICLE_BUTTON = "//*[contains(@content-desc,'Navigate up')]";

    // template methods
    private static String getExistingListTitle(String substring) {
        return EXISTING_LIST_TITLE_TPL.replace("{LIST_TITLE}", substring);
    }
    // template methods

    public ArticlePageObject(AppiumDriver driver){
        super(driver);
    }

    public WebElement waitForTitleElement(){
        return this.waitForElementPresent(By.id(TITLE), "Cannod find title element on page", 5);
    }

    public String getArticleTitle(){
        WebElement title_element = waitForTitleElement();
        return title_element.getAttribute("text");
    }

    public void swipeToFooter(){
        this.swipeUpToFindElement(By.xpath(FOOTER_ELEMENT),
                "Cannot find footer element on the end of the page", 10);
    }

    public void addArticleToNewList(String name_of_folder){
        //кликаю на кнопку Save на нижней панели
        this.waitForElementAndClick(By.xpath(SAVE_TO_MY_LIST_BUTTON),
                "'Save' bookmark button not found on navigation panel",
                5);

        //во всплывающем окне кликаю ADD TO LIST
        this.waitForElementAndClick(By.xpath(OPTIONS_ADD_TO_MY_LIST_BUTTON),
                "'ADD TO LIST' button not found",
                5);

        //в окне создания нового списка сохраненного пишу название списка
        this.waitForElementAndSendKeys(By.xpath(NEW_LIST_NAME_INPUT),
                name_of_folder,
                "'Name of the list' input not found",
                5);

        //в окне создания нового списка сохраненного нажимаю OK
        this.waitForElementAndClick(By.xpath(MY_LIST_OK_BUTTON),
                "ОК button not found",
                5);
    }

    public void addArticleToExistingList(String folder_name){
        //кликаю на кнопку Save на нижней панели
        this.waitForElementAndClick(By.xpath(SAVE_TO_MY_LIST_BUTTON),
                "'Save' bookmark button not found on navigation panel",
                5);
        //во всплывающем окне кликаю ADD TO LIST
        this.waitForElementAndClick(By.xpath(OPTIONS_ADD_TO_MY_LIST_BUTTON),
                "'ADD TO LIST' button not found",
                5);
        String existing_folder_xpath = getExistingListTitle(folder_name);
        //на экране со списками кликаю на созданный список
        this.waitForElementAndClick(By.xpath(existing_folder_xpath),
                "'" + folder_name + "' not found on the saved lists screen",
                5);
    }

    public void closeArticle(){
        //возвращаюсь со страницы статьи на страницу результатов поиска
        this.waitForElementAndClick(By.xpath(CLOSE_ARTICLE_BUTTON),
                "'Navigate up' from article to search results page not found on toolbar",
                5);
    }

    public void assertArticleHasTitle(){
        this.assertElementPresent(
                By.id(TITLE),
                "Tile defined by id '" + TITLE + "' was not found on article page");
    }
}