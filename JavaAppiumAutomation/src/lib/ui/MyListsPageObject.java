package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class MyListsPageObject extends MainPageObject {

    public static final String
        FOLDER_BY_NAME_TPL = "//*[@resource-id='org.wikipedia:id/item_title'][@text='{FOLDER_NAME}']",
        ARTICLE_BY_TITLE_TPL = "//*[@text='{ARTICLE_TITLE}']";

    public MyListsPageObject(AppiumDriver driver){
        super(driver);
    }
// template methods
    private static String getFolderXPathByName(String folder_name){
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", folder_name);
    }

    private static String getArticleTitleXPath(String article_title){
        return ARTICLE_BY_TITLE_TPL.replace("{ARTICLE_TITLE}", article_title);
    }
    // template methods

    public void openFolderByName(String folder_name){
        String folder_name_xpath = getFolderXPathByName(folder_name);
        this.waitForElementAndClick(By.xpath(folder_name_xpath),
                "'" + folder_name + "' item  not found in Saved screen",
                5);
    }

    public void waitForArticleToAppearByTitle(String article_title){
        String article_title_xpath = getArticleTitleXPath(article_title);
        this.waitForElementPresent(By.xpath(article_title_xpath),
                "'"+ article_title + "' article was not found in the saved list",
                15);
    }

    public void waitForArticleToDisappearByTitle(String article_title){
        String article_title_xpath = getArticleTitleXPath(article_title);
        this.waitForElementNotPresent(By.xpath(article_title_xpath),
                "'"+ article_title + "' still present in the saved list",
                15);
    }

    public void swipeArticleToDelete(String article_title){
        String article_title_xpath = getArticleTitleXPath(article_title);
        this.waitForArticleToAppearByTitle(article_title);
        this.swipeElementToLeft(By.xpath(article_title_xpath),
                "'"+ article_title + "' article not found in the saved list");
        this.waitForArticleToDisappearByTitle(article_title);
    }



}
