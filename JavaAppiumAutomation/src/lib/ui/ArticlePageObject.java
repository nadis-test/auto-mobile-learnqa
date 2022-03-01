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
    MY_LIST_NAME_INPUT = "//*[@resource-id='org.wikipedia:id/text_input']",
    MY_LIST_OK_BUTTON = "//*[@resource-id='android:id/button1'][@text = 'OK']",
    CLOSE_ARTICLE_BUTTON = "//*[contains(@content-desc,'Navigate up')]";



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

    public void addArticleToMyList(String name_of_folder){

        //кликаю на кнопку Save на нижней панели
        this.waitForElementAndClick(By.xpath(SAVE_TO_MY_LIST_BUTTON),
                "'Save' bookmark button not found on navigation panel",
                5);

        //во всплывающем окне кликаю ADD TO LIST
        this.waitForElementAndClick(By.xpath(OPTIONS_ADD_TO_MY_LIST_BUTTON),
                "'ADD TO LIST' button not found",
                5);

        //в окне создания нового списка сохраненного пишу название списка
        this.waitForElementAndSendKeys(By.xpath(MY_LIST_NAME_INPUT),
                name_of_folder,
                "'Name of the list' input not found",
                5);

        //в окне создания нового списка сохраненного нажимаю OK
        this.waitForElementAndClick(By.xpath(MY_LIST_OK_BUTTON),
                "ОК button not found",
                5);
    }

    public void closeArticle(){
        //возвращаюсь со страницы статьи на страницу результатов поиска
        this.waitForElementAndClick(By.xpath(CLOSE_ARTICLE_BUTTON),
                "'Navigate up' from article to search results page not found on toolbar",
                5);
    }
}
