package com.test.qa.main.blocks;

import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.allure.annotations.Step;
import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.element.Link;

@FindBy(css = "div.search-header")
public class SearchFilter extends HtmlElement {
    @FindBy(css = "div.filter-button-container>button")
    private Button expandFilterButton;
    @FindBy(xpath = ".//a[span='Rating']")
    private Link sortByRating;

    @Step("Expand search filter")
    public void expandFilter() {
        if (getAttribute("class").contains("collapsed")) {
            expandFilterButton.click();
        }
    }

    @Step("Click on \'Rating\' link")
    public void sortByRating() {
        sortByRating.click();
    }
}
