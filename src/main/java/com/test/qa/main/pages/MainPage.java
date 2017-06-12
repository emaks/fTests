package com.test.qa.main.pages;

import com.test.qa.main.blocks.Header;
import com.test.qa.utils.Page;
import com.test.qa.utils.Utils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.allure.annotations.Step;
import ru.yandex.qatools.htmlelements.element.HtmlElement;

public class MainPage extends Page<MainPage> {
    private Header headerBlock;
    @FindBy(css = "#progress")
    private HtmlElement loadingProgress;

    public MainPage(WebDriver driver) {
        super(driver);
        setMca("");
        setTitle("YouTube");
    }

    @Step("Find videos by {0} phrase")
    public SearchResultsPage findVideos(String value) {
        headerBlock.findVideos(value);
        new WebDriverWait(driver, 10).until(Utils.invisibilityOf(loadingProgress));
        return new SearchResultsPage(driver, value).validate();
    }
}
