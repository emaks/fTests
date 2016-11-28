package com.test.qa.main.blocks;

import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.allure.annotations.Step;
import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.element.Link;

@FindBy(css = "#main_table")
public class TopNavigation extends HtmlElement {
    @FindBy(css = "span.menu_lang>a")
    private Link languageLink;
    @FindBy(css = "[href$='/search/']")
    private Button searchButton;
    @FindBy(css = "[href$='/favorites/']")
    private Button bookmarksButton;

    @Step("Select {0} language")
    public String changeLanguage(String language) {
        if (languageLink.getText().equalsIgnoreCase(language)) {
            String mcaParam = languageLink.getText().toLowerCase();
            languageLink.click();
            return mcaParam;
        }
        throw new AssertionError(String.format("You can not select '%s' language", language));
    }

    @Step("Click on search button")
    public void openSearch() {
        searchButton.click();
    }

    @Step("Click on bookmarks button")
    public void openBookmarks() {
        bookmarksButton.click();
    }
}
