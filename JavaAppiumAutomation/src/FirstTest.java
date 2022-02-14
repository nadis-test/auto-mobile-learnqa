import io.appium.java_client.AppiumDriver;
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
import java.util.Locale;

import static org.hamcrest.CoreMatchers.containsString;

public class FirstTest {

    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception{

        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("deviceName","emulator-5554");
        capabilities.setCapability("platformVersion","10");
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

        waitForElementAndSendKeys(By.id("org.wikipedia:id/search_src_text"),
                "meme",
                "Cannot find search field input",
                5);

        //убеждаемся, что контейнер с элементами 1-й статьи есть на странице поиска
        waitForElementPresent(
                By.xpath("//*[@class='android.view.ViewGroup'][@instance = 1]"),
                "1-st article wasn't found on search result page",
                15);

        //проверяем, что название 1 статьи содержит поисковый запрос
        assertElementHasText(
                    By.xpath("//*[@class='android.view.ViewGroup'][@instance = 1]//descendant::android.widget.TextView[@resource-id = 'org.wikipedia:id/page_list_item_title']"),
                    "meme",
                    "'Text' attribute for the article title doesn't match with expected 'text' value",
                   5);

        //убеждаемся, что контейнер с элементами 2-й статьи есть на странице поиска [@instance = '2']

        waitForElementPresent(
                By.xpath("//*[@class='android.view.ViewGroup'][@instance = 2]"),
                "2-nd article wasn't found on search result page",
                15);


        //проверяем, что название 2-й статьи содержит поисковый запрос
        assertElementHasText(
                By.xpath("//*[@class='android.view.ViewGroup'][@instance = 2]//descendant::android.widget.TextView[@resource-id = 'org.wikipedia:id/page_list_item_title']"),
                "meme",
                "'Text' attribute for the article title doesn't match with expected 'text' value",
                5);

        //убеждаемся, что контейнер с элементами 3-й статьи есть на странице поиска
        waitForElementPresent(
                By.xpath("//*[@class='android.view.ViewGroup'][@instance = 3]"),
                "3-rd article wasn't found on search result page",
                15);

        //проверяем, что название 3-й статьи содержит поисковый запрос
        assertElementHasText(
                By.xpath("//*[@class='android.view.ViewGroup'][@instance = 3]//descendant::android.widget.TextView[@resource-id = 'org.wikipedia:id/page_list_item_title']"),
                "meme",
                "'Text' attribute for the article title doesn't match with expected 'text' value",
                5);

        //нажимаем крестик в строке поиска -очищаем строку поиска и результаты поиска
        waitForElementAndClick(By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find cancel search button",
                5);

        //убеждаемся, что на экране нет элементов, в которых есть заголовок статьи
        waitForElementNotFound(By.xpath("//*[@resource-id = 'org.wikipedia:id/page_list_item_title']"),
                "There are search results on the page after canceling search",
                15);
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

        Assert.assertThat("my error message",
                element.getAttribute("text").toLowerCase(),
                containsString(text_expected_value.toLowerCase())
        );
    }
}
