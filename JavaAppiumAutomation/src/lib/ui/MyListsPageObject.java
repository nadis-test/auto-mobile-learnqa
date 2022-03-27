package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;

abstract public class MyListsPageObject extends MainPageObject {

    protected static String
        FOLDER_BY_NAME_TPL,
        ARTICLE_BY_TITLE_TPL,
        IOS_DELETE_BUTTON,
        CLOSE_SYNC_OVERLAY_BUTTON;

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
        this.waitForElementAndClick(folder_name_xpath,
                "'" + folder_name + "' item  not found in Saved screen",
                5);
    }

    public void waitForArticleToAppearByTitle(String article_title){
        String article_title_xpath = getArticleTitleXPath(article_title);
        this.waitForElementPresent(article_title_xpath,
                "'"+ article_title + "' article was not found in the saved list",
                15);
    }

    public void waitForArticleToDisappearByTitle(String article_title){
        String article_title_xpath = getArticleTitleXPath(article_title);
        this.waitForElementNotPresent(article_title_xpath,
                "'"+ article_title + "' still present in the saved list",
                15);
    }

    public void swipeArticleToDelete(String article_title){
        String article_title_xpath = getArticleTitleXPath(article_title);
        this.waitForArticleToAppearByTitle(article_title);
        this.swipeElementToLeft(article_title_xpath,
                "'"+ article_title + "' article not found in the saved list");
        if (Platform.getInstance().isIOS()){
            this.waitForElementAndClick(IOS_DELETE_BUTTON, "IOS delete button not found", 5);
        }
        this.waitForArticleToDisappearByTitle(article_title);
    }

    public void closeSyncOverlay(){
        this.waitForElementAndClick(CLOSE_SYNC_OVERLAY_BUTTON, "Close button not found on Sync overlay", 5);
    }



}
