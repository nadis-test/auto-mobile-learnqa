package tests;

import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class SearchTests extends CoreTestCase {
    @Test
    public void testSearch(){
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.skipOnboarding();
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("Object-oriented programming language");
    }

    @Test
    public void testCancelSearch(){
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.skipOnboarding();
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Appium");
        SearchPageObject.waitForCancelButtonToAppear();
        SearchPageObject.clickCancelSearch();
        SearchPageObject.waitForCancelButtonToDisappear();
    }

    @Test
    public void testAmountOfNoEpmtySearch(){
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.skipOnboarding();
        SearchPageObject.initSearchInput();
        String search_line = "Linkin Park diskography";
        SearchPageObject.typeSearchLine(search_line);
        int amount_search_results = SearchPageObject.getAmountOfFoundArticles();
        assertTrue(
                "No search results found",
                amount_search_results > 0
        );
    }

    @Test
    public void testAmountOfEmptySearch(){
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.skipOnboarding();
        SearchPageObject.initSearchInput();
        String search_line = "hlsdfkg";
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.waitForEmptyResultLabel();
        SearchPageObject.assertThereIsNoSearchResult();
    }


}
