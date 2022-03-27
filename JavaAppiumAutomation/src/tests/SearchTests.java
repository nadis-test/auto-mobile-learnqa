package tests;

import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

public class SearchTests extends CoreTestCase {

    @Test
    public void testCheckSearchFieldCaption() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.checkSearchInputCaption("Search Wikipedia");
    }

    @Test
    public void testSearch() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResultByDescription("Object-oriented programming language");
    }

    @Test
    public void testCancelSearch() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Appium");
        SearchPageObject.waitForCancelButtonToAppear();
        SearchPageObject.clickCancelSearch();
        SearchPageObject.waitForCancelButtonToDisappear();
    }

    @Test
    public void testAmountOfNoEpmtySearch() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
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
    public void testAmountOfEmptySearch() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        String search_line = "hlsdfkg";
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.waitForEmptyResultLabel();
        SearchPageObject.assertThereIsNoSearchResult();
    }

    @Test
    public void testCheckSearchResultsAndCancelSearch() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        String search_query = "meme";
        SearchPageObject.typeSearchLine(search_query);
        int c = 1;
        while (c < 4) {
            SearchPageObject.waitForSearchResultByInstance(c);
            SearchPageObject.assertSearchResultByInstanceContainsQuery(c, search_query);
            c++;
        }
        SearchPageObject.clickCancelSearch();
        SearchPageObject.assertThereIsNoSearchResult();
    }

    @Test
    public void testCheckSearchResultsForQuery() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        String search_query = "Java";
        SearchPageObject.typeSearchLine(search_query);

        int search_result_element_count = 1;

        //определяем, есть ли на странице хотя бы один результат поиска
        WebElement search_result_element = SearchPageObject.getSearchResultElementByInstance(search_result_element_count);
        //проверяем, содержится ли в тексте локатора поисковый запрос
        SearchPageObject.assertSearchResultByInstanceContainsQuery(search_result_element_count, search_query);
        while (search_result_element != null) //если на странице есть результат поиска, то переходим к элементу со следующим по порядку локатором
        {
            search_result_element_count++;
            try {
                search_result_element = SearchPageObject.getSearchResultElementByInstance(search_result_element_count);
                SearchPageObject.assertSearchResultByInstanceContainsQuery(search_result_element_count, search_query);
            } catch (TimeoutException exception) //если поиск следующего элемента закончился эксепшеном, то завершаем цикл
            {
                System.out.println("No more search results visible on this page");
                search_result_element = null;
            }
        }
    }

    @Test
    public void testCheckSearchResultByTitleAndDescription(){
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        String search_query = "meme";
        SearchPageObject.typeSearchLine(search_query);
        String title_1 = "Meme";
        String description_1 = "Thought or idea that can be shared, in analogy to a gene";
        SearchPageObject.waitForElementByTitleAndDescription(title_1, description_1);
        String title_2 = "Memento (film)";
        String description_2 = "2000 American film by Christopher Nolan";
        SearchPageObject.waitForElementByTitleAndDescription(title_2, description_2);
        String title_3 = "Memento mori";
        String description_3 = "Artistic or symbolic reminder of the inevitability of death";
        SearchPageObject.waitForElementByTitleAndDescription(title_3, description_3);
    }
}
