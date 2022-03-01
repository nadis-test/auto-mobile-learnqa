import lib.CoreTestCase;
import lib.ui.*;
import org.junit.Test;
import org.openqa.selenium.*;

public class FirstTest extends CoreTestCase {

    private MainPageObject MainPageObject;

    protected void setUp() throws Exception{
        super.setUp();

        MainPageObject = new MainPageObject(driver);
    }

        @Test
        public void testCheckSearchFieldCaption(){

        //закрываем онбординг в новой версии приложения википедии
            MainPageObject.waitForElementAndClick(By.xpath("//*[contains(@text,'SKIP')]"),
                    "SKIP button for onboarding screen not found",
                    5);


            MainPageObject.assertElementHasText(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_container']//descendant::android.widget.TextView"),
                "Search Wikipedia",
                "'Text' attribute for the search field doesn't match with expected 'text' value",
                5);
    }

    @Test
    public void testCheckSearchResultsAndCancelSearch(){
        //закрываем онбординг в новой версии приложения википедии
        MainPageObject.waitForElementAndClick(By.xpath("//*[contains(@text,'SKIP')]"),
                "SKIP button for onboarding screen not found",
                5);

        MainPageObject.waitForElementAndClick(By.id("org.wikipedia:id/search_container"),
                "Cannot find search field",
                5);

        String  search_query = "meme";

        MainPageObject.waitForElementAndSendKeys(By.id("org.wikipedia:id/search_src_text"),
                search_query,
                "Cannot find search field input",
                5);

        int c = 1;
        while (c < 4) {
                //убеждаемся, что контейнер с элементами c-й статьи есть на странице поиска
            MainPageObject.waitForElementPresent(
                        By.xpath("//*[@class='android.view.ViewGroup'][@instance = " + c + "]"),
                        c + " article wasn't found on search result page",
                        15);

                //проверяем, что название статьи содержит поисковый запрос
            MainPageObject.assertElementHasText(
                        By.xpath("//*[@class='android.view.ViewGroup'][@instance = " + c + "]//descendant::android.widget.TextView[@resource-id = 'org.wikipedia:id/page_list_item_title']"),
                        search_query,
                        "'Text' attribute for the " + c + " article title doesn't match with expected '" + search_query + "' value",
                        5);
                c++;
        }

        //нажимаем крестик в строке поиска -очищаем строку поиска и результаты поиска
        MainPageObject.waitForElementAndClick(By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find cancel search button",
                5);

        //убеждаемся, что на экране нет элементов, в которых есть заголовок статьи
        MainPageObject.waitForElementNotPresent(By.xpath("//*[@resource-id = 'org.wikipedia:id/page_list_item_title']"),
                "There are search results on the page after canceling search",
                15);

    }

    @Test
    public void testCheckSearchResultsForQuery(){
        //закрываем онбординг в новой версии приложения википедии
        MainPageObject.waitForElementAndClick(By.xpath("//*[contains(@text,'SKIP')]"),
                "SKIP button for onboarding screen not found",
                5);

        MainPageObject.waitForElementAndClick(By.id("org.wikipedia:id/search_container"),
                "Cannot find search field",
                5);

        String  search_query = "meme";

        MainPageObject.waitForElementAndSendKeys(By.id("org.wikipedia:id/search_src_text"),
                search_query,
                "Cannot find search field input",
                5);

        int count = 1;

        WebElement searchResult = null;
        //локатор ищет page_list_item_title в соотвествующем android.view.ViewGroup
        By article_title = By.xpath("//*[@class='android.view.ViewGroup'][@instance = " + count + "]//descendant::android.widget.TextView[@resource-id = 'org.wikipedia:id/page_list_item_title']");
        //определяем, есть ли на странице хотя бы один результат поиска, содержащий локатор с названием статьи page_list_item_title
        searchResult = MainPageObject.waitForElementPresent(
                article_title,
                "No search results present",
                15);
        //проверяем, содержится ли в тексте локатора поисковый запрос
        MainPageObject.assertElementHasText(article_title, search_query, "Search result " + count + " '" + searchResult.getAttribute("text") + "' doesn't contain search query '"+search_query +"'",5);

        while (searchResult != null) //если на странице есть результат поиска, то переходим к элементу со следующим по порядку локатором
            //можно написать цикл проще, если не использовать waitForElementPresent, а просто сделать перебор WebElements
        {
            count++;
            article_title = By.xpath("//*[@class='android.view.ViewGroup'][@instance = " + count + "]//descendant::android.widget.TextView[@resource-id = 'org.wikipedia:id/page_list_item_title']");
            try {
                searchResult = MainPageObject.waitForElementPresent(
                        article_title,
                        "no article present",
                        15);
                MainPageObject.assertElementHasText(article_title, search_query, "search result " + count + " '" + searchResult.getAttribute("text") + "' doesn't contain search query '"+search_query +"'",5);
            }
            catch (TimeoutException exception) //если поиск следующего элемента закончился эксепшеном, то завершаем цикл
            {
                System.out.println("No more search results visible on this page");
                searchResult = null;
            }

        }
    }









    @Test
    public void testSaveTwoArticlesDeleteOneArticle(){
        //нажимаем SKIP - закрываем онбординг
        MainPageObject.waitForElementAndClick(By.xpath("//*[contains(@text,'SKIP')]"),
                "SKIP button not found",
                5);

        //кликаем по полю поиска
        MainPageObject.waitForElementAndClick(By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find search field",
                5);

        String search_query = "meme";

        //пишем поисковый запрос в поле поиска
        MainPageObject.waitForElementAndSendKeys(By.xpath("//*[@resource-id='org.wikipedia:id/search_src_text']"),
                search_query,
                "Cannot find search field input",
                5);

        //переходим в первую статью
        String article_title_1 = "Memento (film)";

        MainPageObject.waitForElementAndClick(By.xpath("//*[contains(@text,'" + article_title_1 + "')]"),
                "Cannot find search result '"+ article_title_1 + "''",
                5);

        //кликаю на кнопку Save на нижней панели
        MainPageObject.waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/article_menu_bookmark']"),
                "'Save' bookmark button not found on navigation panel for the article '" + article_title_1 + "'",
                5);

        //во всплывающем окне кликаю ADD TO LIST
        MainPageObject.waitForElementAndClick(By.xpath("//*[contains(@text,'ADD TO LIST')]"),
                "'ADD TO LIST' button not found",
                5);

        String saved_list_name = "Mementos list";

        //в окне создания нового списка сохраненного пишу название списка
        MainPageObject.waitForElementAndSendKeys(By.xpath("//*[@resource-id='org.wikipedia:id/text_input']"),
                saved_list_name,
                "'Name of the list' input not found",
                5);

        //в окне создания нового списка сохраненного нажимаю OK
        MainPageObject.waitForElementAndClick(By.xpath("//*[@resource-id='android:id/button1'][@text = 'OK']"),
                "ОК button not found",
                5);

        //возвращаюсь со страницы статьи на страницу результатов поиска
        MainPageObject.waitForElementAndClick(By.xpath("//*[contains(@content-desc,'Navigate up')]"),
                "'Navigate up' from article '" + article_title_1 + "' to search results page not found on toolbar",
                5);

        //переходим во вторую статью
        String article_title_2 = "Memento mori";

        MainPageObject.waitForElementAndClick(By.xpath("//*[contains(@text,'" + article_title_2 + "')]"),
                "Cannot find search result '"+ article_title_2 + "''",
                5);

        //кликаю на кнопку Save на нижней панели
        MainPageObject.waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/article_menu_bookmark']"),
                "'Save' bookmark button not found on navigation panel for the article '" + article_title_2 + "'",
                5);

        //во всплывающем окне кликаю ADD TO LIST
        MainPageObject.waitForElementAndClick(By.xpath("//*[contains(@text,'ADD TO LIST')]"),
                "'ADD TO LIST' button not found",
                5);

        //на экране со списками кликаю на созданный список
        MainPageObject.waitForElementAndClick(By.xpath("//*[@resource-id ='org.wikipedia:id/item_title'][@text = '" + saved_list_name +"']"),
                "'" + saved_list_name + "' not found on the saved lists screen",
                5);

        //возвращаюсь со страницы статьи на страницу результатов поиска
        MainPageObject.waitForElementAndClick(By.xpath("//*[contains(@content-desc,'Navigate up')]"),
                "'Navigate up' from article '" + article_title_2 + "' to search results page not found on toolbar",
                5);

        //возвращаюсь со страницы результатов поиска на главную страницу
        MainPageObject.waitForElementAndClick(By.xpath("//*[@resource-id = 'org.wikipedia:id/search_toolbar']//*[@class = 'android.widget.ImageButton']"),
                "'Navigate up' from search results page to main page not found on toolbar",
                5);

        //кликаю на кнопку сохраненного на нав панели
        MainPageObject.waitForElementAndClick(By.xpath("//*[contains(@content-desc, 'Saved')]"),
                "'Saved' button not found on navigation panel on main page",
                15);

        //кликаю на созданный список в списке сохраненного
        MainPageObject.waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/item_title'][@text='" + saved_list_name + "']"),
                "'" + saved_list_name + "' list not found in 'Saved' screen",
                5);


        //проверяю, что в списке сохранилась первая статья
        MainPageObject.waitForElementPresent(By.xpath("//*[@resource-id = 'org.wikipedia:id/page_list_item_title'][@text = '" + article_title_1 +"']"),
                "'" + article_title_1 + "' article not found in the saved list",
                5);

        //проверяю, что в списке сохранилась вторая статья
        MainPageObject.waitForElementPresent(By.xpath("//*[@resource-id = 'org.wikipedia:id/page_list_item_title'][@text = '" + article_title_2 +"']"),
                "'" + article_title_2 + "' article not found in the saved list",
                5);

        //свайпаю для удаления первой статьи из списка
        MainPageObject.swipeElementToLeft(By.xpath("//*[@text='" + article_title_1 + "']"),
                "'"+ article_title_1 + "' article not found in the saved list when trying to delete article");


        //проверяю, что в списке отображается вторая статья после удаления первой
        MainPageObject.waitForElementPresent(By.xpath("//*[@resource-id = 'org.wikipedia:id/page_list_item_title'][@text = '" + article_title_2 +"']"),
                "'" + article_title_2 + "' article not found in the saved list after deletion '" + article_title_1+ "'",
                5);

        //проверяю, что первая статья не отображается после удаления
        MainPageObject.assertElementNotPresent(By.xpath("//*[@text='" + article_title_1 + "']"),
                "'"+ article_title_1 + "' still present in the list after deletion");

    }

    @Test
    public void testArticleHasTitleElement() {
        //нажимаем SKIP - закрываем онбординг
        MainPageObject.waitForElementAndClick(By.xpath("//*[contains(@text,'SKIP')]"),
                "SKIP button not found",
                5);

        //кликаем по полю поиска
        MainPageObject.waitForElementAndClick(By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find search field",
                5);

        String search_query = "meme";

        //пишем поисковый запрос в поле поиска
        MainPageObject.waitForElementAndSendKeys(By.xpath("//*[@resource-id='org.wikipedia:id/search_src_text']"),
                search_query,
                "Cannot find search field input",
                5);

        //переходим в  статью
        String article_title = "Memento (film)";
        MainPageObject.waitForElementAndClick(By.xpath("//*[contains(@text,'" + article_title + "')]"),
                "Cannot find search result '" + article_title + "''",
                20);

        //String article_title_locator = "//*[@resource-id ='pcs']//child::android.view.View[@instance = '1']";
        String article_title_locator = "pcs-edit-section-title-description";

        MainPageObject.assertElementPresent(
                By.id(article_title_locator),
               "Element defined by '" + article_title_locator +"' not found on article page '" + article_title +"'");
    }





}
