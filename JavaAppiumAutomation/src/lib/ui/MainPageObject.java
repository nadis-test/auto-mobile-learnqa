package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;

public class MainPageObject {
    protected AppiumDriver driver;

    public MainPageObject(AppiumDriver driver){
        this.driver = driver;
    }

    public void assertElementPresent(By by, String error_message){
        int amount_of_elements = getAmountOfElements(by);

        if (amount_of_elements == 0){
            String default_message = "Element '" + by.toString() +"' supposed to be present;";
            throw new AssertionError(default_message + " " + error_message);
        }

    }


    public WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds){
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

    public WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds){
        WebElement element = waitForElementPresent(by,error_message,timeoutInSeconds);
        element.click();
        return element;
    }

    public WebElement waitForElementAndSendKeys(By by, String keys_value, String error_message, long timeoutInSeconds){
        WebElement element = waitForElementPresent(by,error_message,timeoutInSeconds);
        element.sendKeys(keys_value);
        return element;
    }


    public boolean waitForElementNotPresent(By by, String error_message, long timeoutInSeconds){
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public WebElement waitForElementAndClear(By by, String error_message, long timeoutInSeconds){
        WebElement element = waitForElementPresent(by,error_message,timeoutInSeconds);
        element.clear();
        return element;
    }

    public void assertElementHasText(By by, String text_expected_value, String error_message,long timeoutInSeconds){
        WebElement element = waitForElementPresent(by, "Cannot find element", timeoutInSeconds);
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
                press(x,start_y).
                waitAction(timeOfSwipe).
                moveTo(x, end_y).
                release().
                perform();

    }

    public void swipeUpQuick(){
        swipeUp(200);
    }

    public void swipeUpToFindElement(By by, String error_message, int max_swipes){
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

    public void swipeElementToLeft(By by, String error_message){
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

    public int getAmountOfElements(By by){
        List elements = driver.findElements(by);
        return elements.size();

    }

    public void assertElementNotPresent(By by, String error_message){
        int amount_of_elements = getAmountOfElements(by);
        if (amount_of_elements > 0){
            String default_message = "Element '" + by.toString() +"' supposed to be not present";
            throw new AssertionError(default_message + " " + error_message);
        }

    }

    public String waitForElementAndGetAttribute(By by, String attribute, String error_message, long timeout_in_seconds){
        WebElement element = waitForElementPresent(by, error_message, timeout_in_seconds);
        return element.getAttribute(attribute);
    }
}
