package com.test.qa.search.pages;

import com.test.qa.search.blocks.SearchPhraseInput;
import com.test.qa.utils.Page;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.allure.annotations.Step;
import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.element.TextInput;

public class SearchPage extends Page<SearchPage> {
    private final String groupName;
    private final String groupMca;
    private SearchPhraseInput searchPhrase;
    @FindBy(css = "[name='topt[8][min]']")
    private TextInput minPrice;
    @FindBy(css = "[name='topt[8][max]']")
    private TextInput maxPrice;
    @FindBy(css = "#sbtn")
    private Button submitSearchButton;

    public SearchPage(WebDriver driver, String groupName, String groupMca) {
        super(driver);
        this.groupName = groupName;
        this.groupMca = groupMca;
        setTitle("SS.lv " + groupName + ". Искать объявления. Поиск объявлений");
        setMca(groupMca + "/search/");
    }

    @Step("Fill in {0} value in 'search phase' field")
    public SearchPage fillSearchPhrase(String phrase) {
        searchPhrase.fill(phrase);
        return this;
    }

    @Step("Fill in {0}-{1} values in price range fields")
    public SearchPage fillPriceRange(String from, String to) {
        minPrice.sendKeys(from);
        maxPrice.sendKeys(to);
        return this;
    }

    @Step("Submit search form")
    public SearchResultPage submitSearch() {
        submitSearchButton.click();
        return new SearchResultPage(driver, groupName, groupMca).setLanguage(getLanguage())
                .validate()
                .verifyResultExist();
    }
}
