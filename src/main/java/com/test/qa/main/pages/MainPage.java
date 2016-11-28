package com.test.qa.main.pages;

import com.test.qa.advertisement.blocks.AdGroup;
import com.test.qa.advertisement.pages.AdGroupPage;
import com.test.qa.main.blocks.TopNavigation;
import com.test.qa.utils.Page;
import org.openqa.selenium.WebDriver;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.List;

public class MainPage extends Page<MainPage> {
    private TopNavigation topNavigationBlock;
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private List<AdGroup> adGroups;

    public MainPage(WebDriver driver) {
        super(driver);
        setMca("");
    }

    @Step("Select {0} language")
    public void changeLanguage(String value) {
        setLanguage(topNavigationBlock.changeLanguage(value));
        validate();
    }

    @Step("Open ad group with name {0}")
    public AdGroupPage openAdGroup(String name) {
        AdGroup expectedGroup = adGroups.stream().filter(group -> group.getGroupName().equals(name))
                .findFirst()
                .orElseThrow(() -> new AssertionError("There is no group with name equal to " + name));
        String mca = expectedGroup.open();
        return new AdGroupPage(driver, name, mca).setLanguage(getLanguage()).validate();
    }

    @Override
    public String getTitle() {
        return "ru".equals(getLanguage()) ? "Объявления - SS.LV" : "Sludinājumi - SS.LV";
    }
}
