package tests.iOS;

import lib.ui.IOSTestCase;
import lib.ui.WelcomePageObject;
import org.junit.Test;

public class GetStartedTest extends IOSTestCase {

    @Test
    public void testPassThroghWelcome(){
        WelcomePageObject WelcomePage = new WelcomePageObject(driver);

        WelcomePage.waitForMoreLearnLink();
        WelcomePage.clickNextButton();
        WelcomePage.waitForNewWaysToExplore();
        WelcomePage.clickNextButton();
        WelcomePage.waitForAddAndEditPreferredLangText();
        WelcomePage.clickNextButton();
        WelcomePage.waitForLearnMoreAboutDataCollectedText();
        WelcomePage.clickGetStartedButton();
    }
}
