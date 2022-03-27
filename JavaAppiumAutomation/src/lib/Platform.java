package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.net.URL;

public class Platform {
    private static final String
            PLATFORM_ANDROID = "android",
            PLATFORM_IOS = "ios",
            APPIUM_URL = "http://127.0.0.1:4723/wd/hub";

    private static Platform instance;

    private Platform() {}

    public static Platform getInstance(){
        if (instance == null){
            instance = new Platform();
        }
        return instance;
    }

    public AppiumDriver getDriver() throws Exception{
        URL URL = new URL(APPIUM_URL);
        if (this.isAndroid()) {
            return new AndroidDriver(URL, this.getAndroidDesiredCapabilities());
        } else if (this.isIOS()) {
            return new IOSDriver(URL, this.getIOSDesiredCapabilities());
        } else {
            throw new Exception("Cannot detect type of the Appium Driver. Platform value = " + this.getPlatformVar());
        }
    }

    public boolean isAndroid(){
        return isPlatform(PLATFORM_ANDROID);
    }

    public boolean isIOS(){
        return isPlatform(PLATFORM_IOS);
    }

    private DesiredCapabilities getCapabilitiesByPlatformEnv() throws Exception {
        String platform = System.getenv("PLATFORM");

        DesiredCapabilities capabilities = new DesiredCapabilities();

        if (platform.equals(PLATFORM_ANDROID)) {
            capabilities.setCapability("platformName", "Android");
            capabilities.setCapability("deviceName", "emulator-5554");
            capabilities.setCapability("platformVersion", "10");
            //capabilities.setCapability("deviceName","emulator-5556");
            //capabilities.setCapability("platformVersion","8.0.0");
            capabilities.setCapability("automationName", "Appium");
            capabilities.setCapability("appPackage", "org.wikipedia");
            capabilities.setCapability("appActivity", ".main.MainActivity");
            capabilities.setCapability("app", "/Users/n.porotkova/GitHub/auto-mobile-learnqa/JavaAppiumAutomation/apks/org.wikipedia.apk");
            capabilities.setCapability("orientation", "PORTRAIT");
        } else if (platform.equals(PLATFORM_IOS)) {
            capabilities.setCapability("platformName", "IOS");
            capabilities.setCapability("deviceName", "iPhone SE (3rd generation)");
            capabilities.setCapability("platformVersion", "15.4");
            capabilities.setCapability("app", "/Users/n.porotkova/GitHub/auto-mobile-learnqa/JavaAppiumAutomation/apks/Wikipedia.app");
            capabilities.setCapability("orientation", "PORTRAIT");
        } else {
            throw new Exception("Cannot get platform from env variable. Platform value: " + platform);
        }
        return capabilities;
    }

    private DesiredCapabilities getAndroidDesiredCapabilities(){
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "emulator-5554");
        capabilities.setCapability("platformVersion", "10");
        //capabilities.setCapability("deviceName","emulator-5556");
        //capabilities.setCapability("platformVersion","8.0.0");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "/Users/n.porotkova/GitHub/auto-mobile-learnqa/JavaAppiumAutomation/apks/org.wikipedia.apk");
        capabilities.setCapability("orientation", "PORTRAIT");
        return capabilities;
    }

    private DesiredCapabilities getIOSDesiredCapabilities(){
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "IOS");
        capabilities.setCapability("deviceName", "iPhone 11");
        capabilities.setCapability("platformVersion", "14.5");
        capabilities.setCapability("app", "/Users/n.porotkova/GitHub/auto-mobile-learnqa/JavaAppiumAutomation/apks/Wikipedia.app");
        capabilities.setCapability("orientation", "PORTRAIT");
        return capabilities;
    }

    private boolean isPlatform(String my_platform){
        String platform = this.getPlatformVar();
        return my_platform.equals(platform);
    }

    private String getPlatformVar(){
        return System.getenv("PLATFORM");
    }
}