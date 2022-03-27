package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import org.openqa.selenium.WebElement;

abstract public class ArticlePageObject extends MainPageObject {
    protected static String
        TITLE,
        FOOTER_ELEMENT,
        SAVE_TO_MY_LIST_BUTTON,
        OPTIONS_ADD_TO_MY_LIST_BUTTON,
        NEW_LIST_NAME_INPUT,
        MY_LIST_OK_BUTTON,
        EXISTING_LIST_TITLE_TPL,
        CLOSE_ARTICLE_BUTTON;

    // template methods
    private static String getExistingListTitle(String substring) {
        return EXISTING_LIST_TITLE_TPL.replace("{LIST_TITLE}", substring);
    }
    // template methods

    public ArticlePageObject(AppiumDriver driver){
        super(driver);
    }

    public WebElement waitForTitleElement(){
        return this.waitForElementPresent(TITLE, "Cannot find title element on page", 5);
    }

    public String getArticleTitle(){
        WebElement title_element = waitForTitleElement();
        if (Platform.getInstance().isAndroid()) {
            return title_element.getAttribute("text");
        } else { return title_element.getAttribute("label"); }
    }

    public void swipeToFooter(){
        System.out.println("swipeToFooter()");
        if (Platform.getInstance().isAndroid()){
        this.swipeUpToFindElement(FOOTER_ELEMENT,
                "Cannot find footer element on the end of the page", 40);
        } else this.swipeUpTillElementAppears(FOOTER_ELEMENT, "Cannot find footer element on the end of the page",40);
    }

    public void addArticleToNewList(String name_of_folder){
        //кликаю на кнопку Save на нижней панели
        this.waitForElementAndClick(SAVE_TO_MY_LIST_BUTTON,
                "'Save' bookmark button not found on navigation panel",
                5);

        //во всплывающем окне кликаю ADD TO LIST
        this.waitForElementAndClick(OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "'ADD TO LIST' button not found",
                5);

        //в окне создания нового списка сохраненного пишу название списка
        this.waitForElementAndSendKeys(NEW_LIST_NAME_INPUT,
                name_of_folder,
                "'Name of the list' input not found",
                5);

        //в окне создания нового списка сохраненного нажимаю OK
        this.waitForElementAndClick(MY_LIST_OK_BUTTON,
                "ОК button not found",
                5);
    }

    public void addArticleToExistingList(String folder_name){
        //кликаю на кнопку Save на нижней панели
        this.waitForElementAndClick(SAVE_TO_MY_LIST_BUTTON,
                "'Save' bookmark button not found on navigation panel",
                5);
        //во всплывающем окне кликаю ADD TO LIST
        this.waitForElementAndClick(OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "'ADD TO LIST' button not found",
                5);
        String existing_folder_xpath = getExistingListTitle(folder_name);
        //на экране со списками кликаю на созданный список
        this.waitForElementAndClick(existing_folder_xpath,
                "'" + folder_name + "' not found on the saved lists screen",
                5);
    }

    public void closeArticle(){
        //возвращаюсь со страницы статьи на страницу результатов поиска
        this.waitForElementAndClick(CLOSE_ARTICLE_BUTTON,
                "'Navigate up' from article to search results page not found on toolbar",
                5);
    }

    public void assertArticleHasTitle(){
        this.assertElementPresent(
                TITLE,
                "Tile defined by id '" + TITLE + "' was not found on article page");
    }

    public void addArticlesToMySaved(){
        this.waitForElementAndClick(SAVE_TO_MY_LIST_BUTTON, "Save button not found", 5);
    }
}
