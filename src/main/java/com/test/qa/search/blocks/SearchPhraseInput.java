package com.test.qa.search.blocks;

import com.test.qa.utils.Utils;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.element.HtmlElement;

import java.util.List;

@FindBy(css = "#ptxt")
public class SearchPhraseInput extends HtmlElement {
    private Suggestion suggestions;

    public void fill(String value) {
        sendKeys(value);
        if (suggestions.exists() && suggestions.isDisplayed()) {
            Utils.pause(1); //Don't do it! Can not find any visible condition for waiting the end of js query execution
            suggestions.selectSuggestion(value);
        }
    }

    @FindBy(xpath = "following-sibling::div[div/@id='preload_txt']")
    public static class Suggestion extends HtmlElement {
        @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
        @FindBy(css = "[id^='cmp_']")
        private List<HtmlElement> suggestions;

        public void selectSuggestion(String value) {
            for (HtmlElement suggestion : suggestions) {
                if (suggestion.getText().equals(value)) {
                    suggestion.click();
                    return;
                }
            }
        }
    }
}
