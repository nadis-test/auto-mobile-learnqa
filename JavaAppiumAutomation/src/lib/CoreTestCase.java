package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import junit.framework.TestCase;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.time.Duration;

public class CoreTestCase extends TestCase {

    private static final String
        PLATFORM_ANDROID = "android",
        PLATFORM_IOS = "ios";

    protected AppiumDriver driver;
    private static String AppiumURL = "http://127.0.0.1:4723/wd/hub";

    @Override
    protected void setUp() throws Exception{

        super.setUp();

        DesiredCapabilities capabilities = this.getCapabilitiesByPlatformEnv();

        driver = new AndroidDriver(new URL(AppiumURL), capabilities);
        this.rotateScreenPortrait();
    }

    @Override
    protected void tearDown() throws Exception {
        driver.quit();
        super.tearDown();
    }

    protected void rotateScreenLandscape(){
        driver.rotate(ScreenOrientation.LANDSCAPE);
    }

    protected void rotateScreenPortrait(){
        driver.rotate(ScreenOrientation.PORTRAIT);
    }

    protected void backgroundApp(int seconds){
        driver.runAppInBackground(Duration.ofSeconds(seconds));
    }

    private DesiredCapabilities getCapabilitiesByPlatformEnv() throws Exception{
        String platform = System.getenv("PLATFORM");

        DesiredCapabilities capabilities = new DesiredCapabilities();

        if (platform.equals(PLATFORM_ANDROID)) {
            capabilities.setCapability("platformName","Android");
            capabilities.setCapability("deviceName","emulator-5554");
            capabilities.setCapability("platformVersion","10");
            //capabilities.setCapability("deviceName","emulator-5556");
            //capabilities.setCapability("platformVersion","8.0.0");
            capabilities.setCapability("automationName","Appium");
            capabilities.setCapability("appPackage","org.wikipedia");
            capabilities.setCapability("appActivity",".main.MainActivity");
            capabilities.setCapability("app","/Users/n.porotkova/GitHub/auto-mobile-learnqa/JavaAppiumAutomation/apks/org.wikipedia.apk");
            capabilities.setCapability("orientation","PORTRAIT");
        }
        else if (platform.equals(PLATFORM_IOS)) {
            capabilities.setCapability("platformName", "IOS");
            capabilities.setCapability("deviceName", "iPhone SE (3rd generation)");
            capabilities.setCapability("platformVersion", "15.4");
            capabilities.setCapability("app", "/Users/n.porotkova/GitHub/auto-mobile-learnqa/JavaAppiumAutomation/apks/Wikipedia.app");
            capabilities.setCapability("orientation", "PORTRAIT");
        }
        else {
            throw new Exception("Cannot get platform from env variable. Platform value: " + platform);
        }

        return capabilities;
    }
}
