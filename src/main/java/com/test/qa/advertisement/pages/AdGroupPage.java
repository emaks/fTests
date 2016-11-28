package com.test.qa.advertisement.pages;

import com.test.qa.main.blocks.TopNavigation;
import com.test.qa.search.pages.SearchPage;
import com.test.qa.utils.Page;
import org.openqa.selenium.WebDriver;
import ru.yandex.qatools.allure.annotations.Step;

public class AdGroupPage extends Page<AdGroupPage> {
    private final String groupName;
    private final String groupMca;
    private TopNavigation topNavigationBlock;

    public AdGroupPage(WebDriver driver, String groupName, String groupMca) {
        super(driver);
        this.groupName = groupName;
        this.groupMca = groupMca;
        setMca(this.groupMca + "/");
        setTitle("SS.lv " + this.groupName + " - Объявления");
    }

    @Step("Open search page")
    public SearchPage openSearch() {
        topNavigationBlock.openSearch();
        return new SearchPage(driver, groupName, groupMca).setLanguage(getLanguage()).validate();
    }
}
