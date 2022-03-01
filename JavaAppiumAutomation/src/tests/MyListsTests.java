package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class MyListsTests extends CoreTestCase {
    @Test
    public void testSaveFirstArticleToMyList(){

        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.skipOnboarding();
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithDescription("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        String article_title = ArticlePageObject.getArticleTitle();

        String folder_name = "my list";
        ArticlePageObject.addArticleToNewList(folder_name);

        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = new NavigationUI(driver);
        NavigationUI.returnFromSearchResultsToMainPage();
        NavigationUI.clickSavedLists();

        MyListsPageObject MyListsPageObject = new MyListsPageObject(driver);
        MyListsPageObject.openFolderByName(folder_name);
        MyListsPageObject.swipeArticleToDelete(article_title);
    }

    @Test
    public void testSaveTwoArticlesDeleteOneArticle(){
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.skipOnboarding();
        SearchPageObject.initSearchInput();
        String search_query = "meme";
        SearchPageObject.typeSearchLine(search_query);
        String article_title_1 = "Memento (film)";
        SearchPageObject.clickByArticleWithTitle(article_title_1);

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        String folder_name = "Mementos list";
        ArticlePageObject.addArticleToNewList(folder_name);
        ArticlePageObject.closeArticle();

        String article_title_2 = "Memento mori";
        SearchPageObject.clickByArticleWithTitle(article_title_2);
        ArticlePageObject.addArticleToExistingList(folder_name);
        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = new NavigationUI(driver);
        NavigationUI.returnFromSearchResultsToMainPage();
        NavigationUI.clickSavedLists();

        MyListsPageObject MyListsPageObject = new MyListsPageObject(driver);
        MyListsPageObject.openFolderByName(folder_name);
        MyListsPageObject.waitForArticleToAppearByTitle(article_title_1);
        MyListsPageObject.waitForArticleToAppearByTitle(article_title_2);
        MyListsPageObject.swipeArticleToDelete(article_title_1);
        MyListsPageObject.waitForArticleToDisappearByTitle(article_title_1);
        MyListsPageObject.waitForArticleToAppearByTitle(article_title_2);
    }
}
