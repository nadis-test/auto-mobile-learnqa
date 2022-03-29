package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.factories.*;
import org.junit.Test;

public class MyListsTests extends CoreTestCase {
    private static final String FOLDER_NAME = "my list";

    @Test
    public void testSaveFirstArticleToMyList(){

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithDescription("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        String article_title = ArticlePageObject.getArticleTitle();
        if (Platform.getInstance().isAndroid()) {
            ArticlePageObject.addArticleToNewList(FOLDER_NAME);
        } else {
            ArticlePageObject.addArticlesToMySaved();
        }

        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.returnFromSearchResultsToMainPage();
        NavigationUI.clickSavedLists();

        MyListsPageObject MyListsPageObject = MyListPageObjectFactory.get(driver);
        if (Platform.getInstance().isAndroid()) {
            MyListsPageObject.openFolderByName(FOLDER_NAME);
        } else {MyListsPageObject.closeSyncOverlay();}
        MyListsPageObject.swipeArticleToDelete(article_title);
    }

    @Test
    public void testSaveTwoArticlesDeleteOneArticle(){
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        String search_query = "meme";
        SearchPageObject.typeSearchLine(search_query);
        String article_title_1 = "Memento (film)";
        SearchPageObject.clickByArticleWithTitle(article_title_1);

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);;
        if (Platform.getInstance().isAndroid()) {
            ArticlePageObject.addArticleToNewList(FOLDER_NAME);
        } else {
            ArticlePageObject.addArticlesToMySaved();
        }
        ArticlePageObject.closeArticle();

        String article_title_2 = "Memento mori";
        SearchPageObject.clickByArticleWithTitle(article_title_2);
        if (Platform.getInstance().isAndroid()) {
            ArticlePageObject.addArticleToNewList(FOLDER_NAME);
        } else {
            ArticlePageObject.addArticlesToMySaved();
        }
        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.returnFromSearchResultsToMainPage();
        NavigationUI.clickSavedLists();

        MyListsPageObject MyListsPageObject = MyListPageObjectFactory.get(driver);
        if (Platform.getInstance().isAndroid()) {
            MyListsPageObject.openFolderByName(FOLDER_NAME);
        } else {MyListsPageObject.closeSyncOverlay();}
        MyListsPageObject.waitForArticleToAppearByTitle(article_title_1);
        MyListsPageObject.waitForArticleToAppearByTitle(article_title_2);
        MyListsPageObject.swipeArticleToDelete(article_title_1);
        MyListsPageObject.waitForArticleToDisappearByTitle(article_title_1);
        MyListsPageObject.waitForArticleToAppearByTitle(article_title_2);
    }
}
