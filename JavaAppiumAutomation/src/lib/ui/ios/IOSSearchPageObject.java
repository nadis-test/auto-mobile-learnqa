package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.SearchPageObject;

public class IOSSearchPageObject extends SearchPageObject {
    static {
        SEARCH_INIT_ELEMENT = "-ios class chain:**/XCUIElementTypeSearchField[`label == \"Search Wikipedia\"`]";
        SEARCH_INPUT = "id:Search Wikipedia";
        SEARCH_RESULT_DESCRIPTION_BY_SUBSTRING_TPL = "xpath://XCUIElementTypeStaticText[contains(@name,'{SUBSTRING}')]";
        SEARCH_RESULT_TITLE_BY_SUBSTRING_TPL = "xpath://XCUIElementTypeStaticText[contains(@name,'{SUBSTRING}')][@index = '0' or @index = '1']";
        SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL = "xpath://XCUIElementTypeStaticText[contains(@label,'{TITLE}')]//following::XCUIElementTypeStaticText[contains(@name,'{DESCRIPTION}')]";
        SEARCH_CANCEL_BUTTON = "-ios class chain::**/XCUIElementTypeStaticText[`label == 'Cancel'`]";
        SEARCH_RESULT_ELEMENT = "xpath://XCUIElementTypeCell";
        EMPTY_RESULT_ELEMENT = "id:No results found";
        SEARCH_RESULT_ELEMENT_BY_INSTANCE_TPL = "xpath://XCUIElementTypeCell[@index = {INSTANCE_NUMBER}]";
    }
    public IOSSearchPageObject(AppiumDriver driver){
        super(driver);
    }
}
