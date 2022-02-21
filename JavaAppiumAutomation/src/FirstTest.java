import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;

public class FirstTest {

    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception{

        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("deviceName","emulator-5554");
        capabilities.setCapability("platformVersion","10");
        //capabilities.setCapability("deviceName","emulator-5556");
        //capabilities.setCapability("platformVersion","8.0.0");
        capabilities.setCapability("automationName","Appium");
        capabilities.setCapability("appPackage","org.wikipedia");
        capabilities.setCapability("appActivity",".main.MainActivity");
        capabilities.setCapability("app","/Users/n.porotkova/GitHub/auto-mobile-learnqa/JavaAppiumAutomation/apks/org.wikipedia.apk");


        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);

    }

    @After
    public void tearDown(){
        driver.quit();
    }


    @Test
    public void firstTest(){
        waitForElementAndClick(By.xpath("//*[contains(@text,'SKIP')]"),
                                "SKIP button not found",
                                5);

        waitForElementAndClick(By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                                "Canno't find search field",
                                5);

        waitForElementAndSendKeys(By.xpath("//*[@resource-id='org.wikipedia:id/search_src_text']"),
                            "Java",
                            "Cannot find input",
                            5);

        waitForElementPresent(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_description'][contains(@text,'Object-oriented')]"),
                             "Cannot find java",
                            15);

    }



    @Test
    public void cancelSearch(){
        waitForElementAndClick(By.id("org.wikipedia:id/fragment_onboarding_skip_button"),
                "SKIP button not found",
                5);

        waitForElementAndClick(By.id("org.wikipedia:id/search_container"),
                "Canno't find search field",
                5);

        waitForElementAndSendKeys(By.id("org.wikipedia:id/search_src_text"),
                "Appium",
                "Cannot find input",
                5);

        waitForElementAndClear(By.id("org.wikipedia:id/search_src_text"),
                "Cannot find input to clear",
                5);

        waitForElementAndSendKeys(By.id("org.wikipedia:id/search_src_text"),
                "Java",
                "Cannot find input",
                5);

        waitForElementAndClick(By.id("org.wikipedia:id/search_close_btn"),
               "Canno't find cancel search button",
                5);

        waitForElementNotFound(By.id("org.wikipedia:id/search_close_btn"),
                "Cancel search button still present on the screen",
                5);
    }

    @Test
    public void compareArticleTitle() {
        waitForElementAndClick(By.xpath("//*[contains(@text,'SKIP')]"),
                "SKIP button not found",
                5);

        waitForElementAndClick(By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Canno't find search field",
                5);

        waitForElementAndSendKeys(By.xpath("//*[@resource-id='org.wikipedia:id/search_src_text']"),
                "Java",
                "Cannot find search input",
                5);

        waitForElementAndClick(By.xpath("//*[contains(@text,'Object-oriented programming language')]"),
                "Canno't find link to the 'Object-oriented programming language' article",
                5);

        WebElement title_element = waitForElementPresent(By.xpath("//*[@text='Java (programming language)']"),
                "Cannot find article title",
                15);

        String article_title = title_element.getAttribute("text");

        Assert.assertEquals(
                "Unexpected title on the article page",
                "Java (programming language)",
                article_title
        );
    }

        @Test
        public void checkSearchFieldCaption(){

        //закрываем онбординг в новой версии приложения википедии
        waitForElementAndClick(By.xpath("//*[contains(@text,'SKIP')]"),
                    "SKIP button for onboarding screen not found",
                    5);


        assertElementHasText(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_container']//descendant::android.widget.TextView"),
                "Search Wikipedia",
                "'Text' attribute for the search field doesn't match with expected 'text' value",
                5);
    }

    @Test
    public void checkSearchResultsAndCancelSearch(){
        //закрываем онбординг в новой версии приложения википедии
        waitForElementAndClick(By.xpath("//*[contains(@text,'SKIP')]"),
                "SKIP button for onboarding screen not found",
                5);

        waitForElementAndClick(By.id("org.wikipedia:id/search_container"),
                "Cannot find search field",
                5);

        String  search_query = "meme";

        waitForElementAndSendKeys(By.id("org.wikipedia:id/search_src_text"),
                search_query,
                "Cannot find search field input",
                5);

        int c = 1;
        while (c < 4) {
                //убеждаемся, что контейнер с элементами c-й статьи есть на странице поиска
                waitForElementPresent(
                        By.xpath("//*[@class='android.view.ViewGroup'][@instance = " + c + "]"),
                        c + " article wasn't found on search result page",
                        15);

                //проверяем, что название статьи содержит поисковый запрос
                assertElementHasText(
                        By.xpath("//*[@class='android.view.ViewGroup'][@instance = " + c + "]//descendant::android.widget.TextView[@resource-id = 'org.wikipedia:id/page_list_item_title']"),
                        search_query,
                        "'Text' attribute for the " + c + " article title doesn't match with expected '" + search_query + "' value",
                        5);
                c++;
        }

        //нажимаем крестик в строке поиска -очищаем строку поиска и результаты поиска
        waitForElementAndClick(By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find cancel search button",
                5);

        //убеждаемся, что на экране нет элементов, в которых есть заголовок статьи
        waitForElementNotFound(By.xpath("//*[@resource-id = 'org.wikipedia:id/page_list_item_title']"),
                "There are search results on the page after canceling search",
                15);

    }

    @Test
    public void checkSearchResultsForQuery(){
        //закрываем онбординг в новой версии приложения википедии
        waitForElementAndClick(By.xpath("//*[contains(@text,'SKIP')]"),
                "SKIP button for onboarding screen not found",
                5);

        waitForElementAndClick(By.id("org.wikipedia:id/search_container"),
                "Cannot find search field",
                5);

        String  search_query = "meme";

        waitForElementAndSendKeys(By.id("org.wikipedia:id/search_src_text"),
                search_query,
                "Cannot find search field input",
                5);

        int count = 1;

        WebElement searchResult = null;
        //локатор ищет page_list_item_title в соотвествующем android.view.ViewGroup
        By article_title = By.xpath("//*[@class='android.view.ViewGroup'][@instance = " + count + "]//descendant::android.widget.TextView[@resource-id = 'org.wikipedia:id/page_list_item_title']");
        //определяем, есть ли на странице хотя бы один результат поиска, содержащий локатор с названием статьи page_list_item_title
        searchResult = waitForElementPresent(
                article_title,
                "No search results present",
                15);
        //проверяем, содержится ли в тексте локатора поисковый запрос
        assertElementHasText(article_title, search_query, "Search result " + count + " '" + searchResult.getAttribute("text") + "' doesn't contain search query '"+search_query +"'",5);

        while (searchResult != null) //если на странице есть результат поиска, то переходим к элементу со следующим по порядку локатором
            //можно написать цикл проще, если не использовать waitForElementPresent, а просто сделать перебор WebElements
        {
            count++;
            article_title = By.xpath("//*[@class='android.view.ViewGroup'][@instance = " + count + "]//descendant::android.widget.TextView[@resource-id = 'org.wikipedia:id/page_list_item_title']");
            try {
                searchResult = waitForElementPresent(
                        article_title,
                        "no article present",
                        15);
                assertElementHasText(article_title, search_query, "search result " + count + " '" + searchResult.getAttribute("text") + "' doesn't contain search query '"+search_query +"'",5);
            }
            catch (TimeoutException exception) //если поиск следующего элемента закончился эксепшеном, то завершаем цикл
            {
                System.out.println("No more search results visible on this page");
                searchResult = null;
            }

        }
    }

    @Test
    public void testSwipeArticle(){
        //нажимаем SKIP - закрываем онбординг
        waitForElementAndClick(By.xpath("//*[contains(@text,'SKIP')]"),
                "SKIP button not found",
                5);

        //кликаем по полю поиска
        waitForElementAndClick(By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find search field",
                5);

        //пишем поисковый запрос в поле поиска
        waitForElementAndSendKeys(By.xpath("//*[@resource-id='org.wikipedia:id/search_src_text']"),
                "Appium",
                "Cannot find search input",
                5);

        //кликаем по заголовку статьи на странице с результатами поиска и переходим в статью
        waitForElementAndClick(By.xpath("//*[@resource-id = 'org.wikipedia:id/page_list_item_description'][contains(@text,'Automation for Apps')]"),
                "Cannot find link to the 'Appium' article",
                5);

        //проверяем, что в статье правильный заголовок
        waitForElementPresent(By.xpath("//*[@text='Automation for Apps']"),
                "Cannot find article title",
                15);

        //делаем свайп до элемента View article in browser - то есть до конца экрана со статьей
        swipeUpToFindElement(By.xpath("//*[@text='View article in browser']"),
                "Cannot find bottom of the page", 10);


    }

    @Test
    public void saveFirstArticleToMyList(){
        //нажимаем SKIP - закрываем онбординг
        waitForElementAndClick(By.xpath("//*[contains(@text,'SKIP')]"),
                "SKIP button not found",
                5);

        //кликаем по полю поиска
        waitForElementAndClick(By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Canno't find search field",
                5);

        //пишем поисковый запрос в поле поиска
        waitForElementAndSendKeys(By.xpath("//*[@resource-id='org.wikipedia:id/search_src_text']"),
                "Java",
                "Cannot find search input",
                5);

        waitForElementAndClick(By.xpath("//*[contains(@text,'Object-oriented programming language')]"),
                "Canno't find link to the 'Object-oriented programming language' article",
                5);

        String article_title = "Java (programming language)";

        WebElement title_element = waitForElementPresent(By.xpath("//*[@text='" + article_title + "']"),
                "Cannot find title for article '" + article_title + "'" ,
                15);

        //кликаю на кнопку Save на нижней панели
        waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/article_menu_bookmark']"),
                "'Save' bookmark button not found on navigation panel",
                5);

        //во всплывающем окне кликаю ADD TO LIST
        waitForElementAndClick(By.xpath("//*[contains(@text,'ADD TO LIST')]"),
                "'ADD TO LIST' button not found",
                5);

        String saved_list_name = "my list";

        //в окне создания нового списка сохраненного пишу название списка
        waitForElementAndSendKeys(By.xpath("//*[@resource-id='org.wikipedia:id/text_input']"),
                saved_list_name,
                "'Name of the list' input not found",
                5);

        //в окне создания нового списка сохраненного нажимаю OK
        waitForElementAndClick(By.xpath("//*[@resource-id='android:id/button1'][@text = 'OK']"),
                "ОК button not found",
                5);

        //возвращаюсь со страницы статьи на страницу результатов поиска
        waitForElementAndClick(By.xpath("//*[contains(@content-desc,'Navigate up')]"),
                "'Navigate up' from article to search results page not found on toolbar",
                5);

        //возвращаюсь со страницы результатов поиска на главную страницу
        waitForElementAndClick(By.xpath("//*[@resource-id = 'org.wikipedia:id/search_toolbar']//*[@class = 'android.widget.ImageButton']"),
                "'Navigate up' from search results page to main page not found on toolbar",
                5);

        //кликаю на кнопку сохраненного на нав панели
        waitForElementAndClick(By.xpath("//*[contains(@content-desc, 'Saved')]"),
                "Saved button not found on navigation panel",
                5);

        //кликаю на созданный список в списке сохраненного
        waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/item_title'][@text='" + saved_list_name + "']"),
                "'" + saved_list_name + "' item  not found in Save screen",
                5);

        //проверяю свою статью в списке
        assertElementHasText(By.id("org.wikipedia:id/page_list_item_title"),
                article_title,
                "'" + article_title + "' article not found in the saved list",
                5);

        //свайпаю для удаления статьи из списка
        swipeElementToLeft(By.xpath("//*[@text='" + article_title + "']"),
                "'"+ article_title + "' article not found in the saved list");

        //проверяю, что статья не отображается после удаления
        waitForElementNotFound(By.xpath("//*[@text='" + article_title + "']"),
                "'"+ article_title + "' still present in the saved list",
                5);

    }

    @Test
    public void testAmountOfNoEpmtySearch(){
        //нажимаем SKIP - закрываем онбординг
        waitForElementAndClick(By.xpath("//*[contains(@text,'SKIP')]"),
                "SKIP button not found",
                5);

        //кликаем по полю поиска
        waitForElementAndClick(By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find search field",
                5);

        String search_text = "Linkin Park diskography";

        //выполняем поисковый запрос в поле поиска
        waitForElementAndSendKeys(By.xpath("//*[@resource-id='org.wikipedia:id/search_src_text']"),
                search_text,
                "Cannot find search input",
                5);

        String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']//*[@resource-id='org.wikipedia:id/page_list_item_title']";

        //проверяем наличие результата поиска по локатору
        waitForElementPresent(By.xpath(search_result_locator),
                "Cannot find any results by search request: " + search_text,
                15);

        int amount_search_results = getAmountOfElements(By.xpath(search_result_locator));

        Assert.assertTrue(
                "No search results found",
                amount_search_results > 0
        );

    }

    @Test
    public void testAmountOfEmptySearch(){
        //нажимаем SKIP - закрываем онбординг
        waitForElementAndClick(By.xpath("//*[contains(@text,'SKIP')]"),
                "SKIP button not found",
                5);

        //кликаем по полю поиска
        waitForElementAndClick(By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find search field",
                5);

        String search_text = "hlsdfkg";

        //выполняем поисковый запрос в поле поиска
        waitForElementAndSendKeys(By.xpath("//*[@resource-id='org.wikipedia:id/search_src_text']"),
                search_text,
                "Cannot find search input",
                5);

        String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']//*[@resource-id='org.wikipedia:id/page_list_item_title']";
        String empty_result = "//*[@resource-id = 'org.wikipedia:id/results_text'][@text = 'No results']";

        //проверяем наличие элемента пустого результата
        waitForElementPresent(By.xpath(empty_result),
                "Cannot find empty search results label",
                15);

        //проверяем отсутствие результатов поиска
        assertElementNotPresent(By.xpath(search_result_locator),
                "Search results are not empty");

    }

    @Test
    public void testSearchScreenChangeOrientation(){
        //нажимаем SKIP - закрываем онбординг
        waitForElementAndClick(By.xpath("//*[contains(@text,'SKIP')]"),
                "SKIP button not found",
                5);

        //кликаем по полю поиска
        waitForElementAndClick(By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find search field",
                5);

        String search_text = "Java";

        //выполняем поисковый запрос в поле поиска
        waitForElementAndSendKeys(By.xpath("//*[@resource-id='org.wikipedia:id/search_src_text']"),
                search_text,
                "Cannot find search input",
                5);

        String article_title = "Java (programming language)";

        //кликаем на ссылку статьи
        waitForElementAndClick(By.xpath("//*[contains(@text,'"+article_title+"')]"),
                "Cannot find link to the article by search query '" + search_text +"'",
                5);


        //получаем заголовок статьи до переворота экрана
        String article_title_locator = "//*[@resource-id = 'pcs-edit-section-title-description']//preceding-sibling::android.view.View";
        String title_before_rotation = waitForElementAndGetAttribute(By.xpath(article_title_locator),
                "text",
                "Cannot find title of article before",
                15);

        //поворачиваем девайс
        driver.rotate(ScreenOrientation.LANDSCAPE);

        //получаем заголовок статьи после поворота
        String title_after_rotation = waitForElementAndGetAttribute(By.xpath(article_title_locator),
                "text",
                "Cannot find title of article after",
                15);

        //сравниваем заголовок статьи до поворота и после
        Assert.assertEquals("article title has been changed after rotation",
                title_before_rotation,
                title_after_rotation);

        //поворачиваем девайс повторно
        driver.rotate(ScreenOrientation.PORTRAIT);

        //снова получаем заголовок статьи после повторного поворота
        String title_after_second_rotation = waitForElementAndGetAttribute(By.xpath(article_title_locator),
                "text",
                "Cannot find title of article after",
                15);

        //сравниваем заголовок статьи до поворота и после повтороного поворота
        Assert.assertEquals("article title has been changed after second rotation",
                title_before_rotation,
                title_after_second_rotation);

    }

    @Test
    public void testArticleTitleAfterBackground(){
        //нажимаем SKIP - закрываем онбординг
        waitForElementAndClick(By.xpath("//*[contains(@text,'SKIP')]"),
                "SKIP button not found",
                5);

        //кликаем по полю поиска
        waitForElementAndClick(By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Canno't find search field",
                5);

        String search_query = "Java";

        //пишем поисковый запрос в поле поиска
        waitForElementAndSendKeys(By.xpath("//*[@resource-id='org.wikipedia:id/search_src_text']"),
                search_query,
                "Cannot find search input",
                5);

        String article_title = "Java (programming language)";

        waitForElementPresent(By.xpath("//*[contains(@text,'" + article_title + "')]"),
                "Cannot find the article '" + article_title+ "'",
                5);

        driver.runAppInBackground(2);

        waitForElementPresent(By.xpath("//*[contains(@text,'" + article_title + "')]"),
                "After returning from background cannot find the article '" + article_title+ "'",
                5);
    }

    @Test
    public void saveTwoArticlesDeleteOneArticle(){
        //нажимаем SKIP - закрываем онбординг
        waitForElementAndClick(By.xpath("//*[contains(@text,'SKIP')]"),
                "SKIP button not found",
                5);

        //кликаем по полю поиска
        waitForElementAndClick(By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find search field",
                5);

        String search_query = "meme";

        //пишем поисковый запрос в поле поиска
        waitForElementAndSendKeys(By.xpath("//*[@resource-id='org.wikipedia:id/search_src_text']"),
                search_query,
                "Cannot find search field input",
                5);

        //переходим в первую статью
        String article_title_1 = "Memento (film)";

        waitForElementAndClick(By.xpath("//*[contains(@text,'" + article_title_1 + "')]"),
                "Cannot find search result '"+ article_title_1 + "''",
                5);

        //кликаю на кнопку Save на нижней панели
        waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/article_menu_bookmark']"),
                "'Save' bookmark button not found on navigation panel for the article '" + article_title_1 + "'",
                5);

        //во всплывающем окне кликаю ADD TO LIST
        waitForElementAndClick(By.xpath("//*[contains(@text,'ADD TO LIST')]"),
                "'ADD TO LIST' button not found",
                5);

        String saved_list_name = "Mementos list";

        //в окне создания нового списка сохраненного пишу название списка
        waitForElementAndSendKeys(By.xpath("//*[@resource-id='org.wikipedia:id/text_input']"),
                saved_list_name,
                "'Name of the list' input not found",
                5);

        //в окне создания нового списка сохраненного нажимаю OK
        waitForElementAndClick(By.xpath("//*[@resource-id='android:id/button1'][@text = 'OK']"),
                "ОК button not found",
                5);

        //возвращаюсь со страницы статьи на страницу результатов поиска
        waitForElementAndClick(By.xpath("//*[contains(@content-desc,'Navigate up')]"),
                "'Navigate up' from article '" + article_title_1 + "' to search results page not found on toolbar",
                5);

        //переходим во вторую статью
        String article_title_2 = "Memento mori";

        waitForElementAndClick(By.xpath("//*[contains(@text,'" + article_title_2 + "')]"),
                "Cannot find search result '"+ article_title_2 + "''",
                5);

        //кликаю на кнопку Save на нижней панели
        waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/article_menu_bookmark']"),
                "'Save' bookmark button not found on navigation panel for the article '" + article_title_2 + "'",
                5);

        //во всплывающем окне кликаю ADD TO LIST
        waitForElementAndClick(By.xpath("//*[contains(@text,'ADD TO LIST')]"),
                "'ADD TO LIST' button not found",
                5);

        //на экране со списками кликаю на созданный список
        waitForElementAndClick(By.xpath("//*[@resource-id ='org.wikipedia:id/item_title'][@text = '" + saved_list_name +"']"),
                "'" + saved_list_name + "' not found on the saved lists screen",
                5);

        //возвращаюсь со страницы статьи на страницу результатов поиска
        waitForElementAndClick(By.xpath("//*[contains(@content-desc,'Navigate up')]"),
                "'Navigate up' from article '" + article_title_2 + "' to search results page not found on toolbar",
                5);

        //возвращаюсь со страницы результатов поиска на главную страницу
        waitForElementAndClick(By.xpath("//*[@resource-id = 'org.wikipedia:id/search_toolbar']//*[@class = 'android.widget.ImageButton']"),
                "'Navigate up' from search results page to main page not found on toolbar",
                5);

        //кликаю на кнопку сохраненного на нав панели
        waitForElementAndClick(By.xpath("//*[contains(@content-desc, 'Saved')]"),
                "'Saved' button not found on navigation panel on main page",
                15);

        //кликаю на созданный список в списке сохраненного
        waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/item_title'][@text='" + saved_list_name + "']"),
                "'" + saved_list_name + "' list not found in 'Saved' screen",
                5);


        //проверяю, что в списке сохранилась первая статья
        assertElementHasText(By.xpath("//*[@resource-id = 'org.wikipedia:id/page_list_item_title'][@text = '" + article_title_1 +"']"),
                article_title_1,
                "'" + article_title_1 + "' article not found in the saved list",
                5);

        //проверяю, что в списке сохранилась вторая статья
        assertElementHasText(By.xpath("//*[@resource-id = 'org.wikipedia:id/page_list_item_title'][@text = '" + article_title_2 +"']"),
                article_title_2,
                "'" + article_title_2 + "' article not found in the saved list",
                5);

        //свайпаю для удаления первой статьи из списка
        swipeElementToLeft(By.xpath("//*[@text='" + article_title_1 + "']"),
                "'"+ article_title_1 + "' article not found in the saved list when trying to delete article");

        //проверяю, что первая статья не отображается после удаления
        waitForElementNotFound(By.xpath("//*[@text='" + article_title_1 + "']"),
                "'"+ article_title_1 + "' still present in the saved list after deletion",
                5);

        //проверяю, что в списке отображается вторая статья после удаления первой
        waitForElementPresent(By.xpath("//*[@resource-id = 'org.wikipedia:id/page_list_item_title'][@text = '" + article_title_2 +"']"),
                "'" + article_title_2 + "' article not found in the saved list after deletion '" + article_title_1+ "'",
                5);

    }


    private WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds){
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    private WebElement waitForElementPresent(String xpath, String error_message){
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.withMessage(error_message + "\n");
        By by = By.xpath(xpath);
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    private WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds){
        WebElement element = waitForElementPresent(by,error_message,timeoutInSeconds);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String keys_value, String error_message, long timeoutInSeconds){
        WebElement element = waitForElementPresent(by,error_message,timeoutInSeconds);
        element.sendKeys(keys_value);
        return element;
    }


    private boolean waitForElementNotFound(By by, String error_message, long timeoutInSeconds){
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    private WebElement waitForElementAndClear(By by, String error_message, long timeoutInSeconds){
        WebElement element = waitForElementPresent(by,error_message,timeoutInSeconds);
        element.clear();
        return element;
    }

    private void assertElementHasText(By by, String text_expected_value, String error_message,long timeoutInSeconds){
        WebElement element = waitForElementPresent(by, "Cannot find element", timeoutInSeconds);
        System.out.println("element text = " + element.getAttribute("text").toLowerCase());
        Assert.assertThat(error_message,
                element.getAttribute("text").toLowerCase(),
                containsString(text_expected_value.toLowerCase())
        );
    }

    protected void swipeUp(int timeOfSwipe){
        //TouchAction action = new TouchAction(driver);
        TouchAction action = new TouchAction(driver);

        //селениумовским классом получаем размер экрана нашего моб девайса, чтобы потом вычислять относительные координаты для press и других действий
        Dimension size = driver.manage().window().getSize();
        //х - точка посередине по оси х девайса
        int x = size.width / 2;
        //точка внизу 80% экрана девайса - откуда начнем свайп
        int start_y = (int) (size.height * 0.85);
        //точка внизу 20% экрана девайса - где закончим свайп
        int end_y = (int) (size.height * 0.2);

        //нажимаем в точке начала свайпа, ждем таймаут (который передали в метод), перемещаем в точку завершения свайпа, выполняем
        action.
                press(x,start_y).
                waitAction(timeOfSwipe).
                moveTo(x, end_y).
                release().
                perform();

    }

    protected void swipeUpQuick(){
        swipeUp(200);
    }

    protected void swipeUpToFindElement(By by, String error_message, int max_swipes){
        int already_swiped = 0;
        while (driver.findElements(by).size() == 0) {
            if (already_swiped > max_swipes){
                waitForElementPresent(by, "Cannot find element by swiping up \n" + error_message, 0);
                return;
            }
            swipeUpQuick();
            already_swiped++;
        }
    }

    protected void swipeElementToLeft(By by, String error_message){
        WebElement element = waitForElementPresent(by, error_message, 10);

        //находим самую левую точку элемента, который собираемся свайпать
        int left_x = element.getLocation().getX();
        //находим самую правую точку элемента
        int right_x = left_x + element.getSize().getWidth();

        int upper_y = element.getLocation().getY();
        int bottom_y = upper_y + element.getSize().getHeight();
        int middle_y = (upper_y + bottom_y) / 2;

        TouchAction action = new TouchAction(driver);

        action.
                press(right_x, middle_y).
                waitAction(300).
                moveTo(left_x, middle_y).
                release().
                perform();

    }

    private int getAmountOfElements(By by){
        List elements = driver.findElements(by);
        return elements.size();

    }

    private void assertElementNotPresent(By by, String error_message){
        int amount_of_elements = getAmountOfElements(by);
        if (amount_of_elements > 0){
            String default_message = "Element '" + by.toString() +"' supposed to be not present";
            throw new AssertionError(default_message + " " + error_message);
        }

    }

    private String waitForElementAndGetAttribute(By by, String attribute, String error_message, long timeout_in_seconds){
        WebElement element = waitForElementPresent(by, error_message, timeout_in_seconds);
        return element.getAttribute(attribute);
    }

}
