package com.test.qa.main.blocks;

import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.element.TextInput;

@FindBy(css = "#masthead-positioner")
public class Header extends HtmlElement {
    @FindBy(css = "#masthead-search-term")
    private TextInput searchInput;
    @FindBy(css = "#search-btn")
    private Button searchButton;

    public void findVideos(String value) {
        searchInput.sendKeys(value);
        searchButton.click();
    }
}
