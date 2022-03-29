package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import lib.Platform;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.containsString;

public class MainPageObject {
    protected AppiumDriver driver;

    public MainPageObject(AppiumDriver driver){
        this.driver = driver;
    }

    public void assertElementPresent(String locator, String error_message){
        By by = this.getLocatorByString(locator);
        int amount_of_elements = getAmountOfElements(locator);

        if (amount_of_elements == 0){
            String default_message = "Element '" + by.toString() +"' supposed to be present;";
            throw new AssertionError(default_message + " " + error_message);
        }
    }

    public WebElement waitForElementPresent(String locator, String error_message, long timeoutInSeconds){
        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public WebElement waitForElementPresent(String xpath, String error_message){
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.withMessage(error_message + "\n");
        By by = By.xpath(xpath);
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public WebElement waitForElementAndClick(String locator, String error_message, long timeoutInSeconds){
        WebElement element = waitForElementPresent(locator,error_message,timeoutInSeconds);
        element.click();
        return element;
    }

    public WebElement waitForElementAndSendKeys(String locator, String keys_value, String error_message, long timeoutInSeconds){
        WebElement element = waitForElementPresent(locator,error_message,timeoutInSeconds);
        element.sendKeys(keys_value);
        return element;
    }


    public boolean waitForElementNotPresent(String locator, String error_message, long timeoutInSeconds){
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        By by = this.getLocatorByString(locator);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public WebElement waitForElementAndClear(String locator, String error_message, long timeoutInSeconds){
        WebElement element = waitForElementPresent(locator, error_message, timeoutInSeconds);
        element.clear();
        return element;
    }

    public void assertElementHasText(String locator, String text_expected_value, String error_message,long timeoutInSeconds){
        WebElement element = waitForElementPresent(locator, "Cannot find element", timeoutInSeconds);
        System.out.println("element text = " + element.getAttribute("text").toLowerCase());
        Assert.assertThat(error_message,
                element.getAttribute("text").toLowerCase(),
                containsString(text_expected_value.toLowerCase())
        );
    }

    public void swipeUp(int timeOfSwipe){
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
                press(PointOption.point(x, start_y)).
                waitAction(WaitOptions.waitOptions(Duration.ofMillis(timeOfSwipe))).
                moveTo(PointOption.point(x, end_y)).
                release().
                perform();

    }

    public void swipeUpQuick(){
        swipeUp(200);
    }

    public void swipeUpToFindElement(String locator, String error_message, int max_swipes){
        By by = this.getLocatorByString(locator);
        int already_swiped = 0;
        while (driver.findElements(by).size() == 0) {
            if (already_swiped > max_swipes){
                waitForElementPresent(locator, "Cannot find element by swiping up \n" + error_message, 0);
                return;
            }
            swipeUpQuick();
            already_swiped++;
        }
    }

    public void swipeElementToLeft(String locator, String error_message){
        WebElement element = waitForElementPresent(locator, error_message, 10);

        //находим самую левую точку элемента, который собираемся свайпать
        int left_x = element.getLocation().getX();
        //находим самую правую точку элемента
        int right_x = left_x + element.getSize().getWidth();

        int upper_y = element.getLocation().getY();
        int bottom_y = upper_y + element.getSize().getHeight();
        int middle_y = (upper_y + bottom_y) / 2;

        TouchAction action = new TouchAction(driver);

        action.press(PointOption.point(right_x, middle_y));
        action.waitAction(WaitOptions.waitOptions(Duration.ofMillis(300)));
        if (Platform.getInstance().isAndroid()) {
            action.moveTo(PointOption.point(left_x, middle_y));
        } else {
            int offset_x = (-1*element.getSize().getWidth()); //самая левая точка элемента
            action.moveTo(PointOption.point(offset_x, middle_y ));
        }
        action.release();
        action.perform();
    }


    public boolean isElementLocatedOnTheScreen(String locator){
        int element_location_by_y = this.waitForElementPresent(locator, "Cannot find element by locator", 10).getLocation().getY();
        int screen_size_by_window = driver.manage().window().getSize().getHeight();
        return element_location_by_y < screen_size_by_window;
    }

    public void swipeUpTillElementAppears(String locator, String error_message, int max_swipes){
        int already_swiped = 0;
        while (!this.isElementLocatedOnTheScreen(locator)){
            if (already_swiped > max_swipes) {
                Assert.assertTrue(error_message, this.isElementLocatedOnTheScreen(locator));
            }
            swipeUpQuick();
            ++already_swiped;
        }
    }

    public int getAmountOfElements(String locator){
        By by = this.getLocatorByString(locator);
        List elements = driver.findElements(by);
        return elements.size();

    }

    public void assertElementNotPresent(String locator, String error_message){
        int amount_of_elements = getAmountOfElements(locator);
        if (amount_of_elements > 0){
            String default_message = "Element '" + locator.toString() +"' supposed to be not present";
            throw new AssertionError(default_message + " " + error_message);
        }

    }

    public String waitForElementAndGetAttribute(String locator, String attribute, String error_message, long timeout_in_seconds){
        WebElement element = waitForElementPresent(locator, error_message, timeout_in_seconds);
        return element.getAttribute(attribute);
    }

    private By getLocatorByString(String locator_with_type){
        String[] exploded_locator = locator_with_type.split(Pattern.quote(":"),2);
        String by_type = exploded_locator[0];
        String locator = exploded_locator[1];

        if (by_type.equals("xpath"))
            { return By.xpath(locator);}
        else if (by_type.equals("id"))
            { return By.id(locator);}
        else if (by_type.equals("-ios class chain")) {return MobileBy.iOSClassChain(locator);}
        else {throw new IllegalArgumentException("Cannot get type of locator. Locator = " + locator_with_type);}

    }
}
