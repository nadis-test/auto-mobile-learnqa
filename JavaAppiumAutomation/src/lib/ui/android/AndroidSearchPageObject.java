package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.SearchPageObject;

public class AndroidSearchPageObject extends SearchPageObject {
    static {
                SEARCH_INIT_ELEMENT = "xpath://*[@resource-id='org.wikipedia:id/search_container']//descendant::android.widget.TextView";
                SEARCH_INPUT = "xpath://*[@resource-id='org.wikipedia:id/search_src_text']";
                SEARCH_RESULT_DESCRIPTION_BY_SUBSTRING_TPL = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_description'][contains(@text,'{SUBSTRING}')]";
                SEARCH_RESULT_TITLE_BY_SUBSTRING_TPL = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_title'][contains(@text,'{SUBSTRING}')]";
                SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_title'][contains(@text,'{TITLE}')]//following::android.widget.TextView[@resource-id = 'org.wikipedia:id/page_list_item_description'][contains(@text,'{DESCRIPTION}')]";
                SEARCH_CANCEL_BUTTON = "id:org.wikipedia:id/search_close_btn";
                SEARCH_RESULT_ELEMENT = "xpath://*[@resource-id='org.wikipedia:id/search_results_list']//*[@resource-id='org.wikipedia:id/page_list_item_title']";
                EMPTY_RESULT_ELEMENT = "xpath://*[@resource-id = 'org.wikipedia:id/results_text'][@text = 'No results']";
                SEARCH_RESULT_ELEMENT_BY_INSTANCE_TPL = "xpath://*[@class='android.view.ViewGroup'][@instance = {INSTANCE_NUMBER}]//descendant::android.widget.TextView[@resource-id = 'org.wikipedia:id/page_list_item_title']";
    }
    public AndroidSearchPageObject(AppiumDriver driver){
        super(driver);
    }


}
