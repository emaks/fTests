package com.test.qa.search.pages;

import com.test.qa.advertisement.blocks.AdListItem;
import com.test.qa.bookmarks.pages.BookmarksPage;
import com.test.qa.main.blocks.TopNavigation;
import com.test.qa.utils.Page;
import com.test.qa.utils.Utils;
import org.hamcrest.Matchers;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.allure.annotations.Step;
import ru.yandex.qatools.htmlelements.element.*;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

public class SearchResultPage extends Page<SearchResultPage> {
    private TopNavigation topNavigationBlock;

    @FindBy(xpath = ".//a[text()='Цена']")
    private Link sortByPriceLink;
    @FindBy(xpath = ".//span[contains(text(),'Тип сделки:')]/select")
    private Select adType;
    @FindBy(xpath = ".//tbody[input/@name='tsearch']//a[contains(@href,'/search/')]")
    private Link advancedSearchLink;

    @FindBy(xpath = ".//td[contains(text(),'По вашему запросу объявлений не найдено')]")
    private TextBlock noResults;
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private List<AdListItem> foundAds;

    @FindBy(css = "#a_fav_sel")
    private Button addToBookmarksButton;
    @FindBy(css = "#alert_ok")
    private HtmlElement alert;

    public SearchResultPage(WebDriver driver, String groupName, String groupMca) {
        super(driver);
        setTitle("SS.lv " + groupName + ". Результат поиска");
        setMca(groupMca + "/search-result/");
    }

    @Step("Verify that search result is not empty")
    public SearchResultPage verifyResultExist() {
        assertThat("There is no search results", noResults.exists() && noResults.isDisplayed(), Matchers.equalTo(false));
        return this;
    }

    @Step("Sort the results by price")
    public void sortByPrice() {
        sortByPriceLink.click();
    }

    @Step("Select {0} add type")
    public void selectAdType(String value) {
        adType.selectByVisibleText(value);
    }

    @Step("Open advanced search page")
    public void openAdvancedSearch() {
        advancedSearchLink.click();
    }

    @Step("Select {0} random ads")
    public List<String> chooseRandomAds(int count) {
        List<String> ids = new ArrayList<>();
        int adsCount = foundAds.size();
        assertThat("You can not select " + count + " ads", count, Matchers.lessThan(adsCount));
        List<Integer> randomOrder = Utils.getRandomListOrder(adsCount - 1);
        for (int i = 0; i < count; i++) {
            AdListItem ad = foundAds.get(randomOrder.get(i));
            ad.selectAd();
            ids.add(ad.getAdId());
        }
        return ids;
    }

    @Step("Add ads to bookmarks")
    public void addToBookmarks() {
        addToBookmarksButton.click();
        assertThat("There is no confirmation alert", alert.exists() && alert.isDisplayed());
        alert.click();
    }

    @Step("Open bookmarks page")
    public BookmarksPage openBookmarks() {
        ((Locatable) topNavigationBlock.getWrappedElement()).getCoordinates().inViewPort();
        topNavigationBlock.openBookmarks();
        return new BookmarksPage(driver).setLanguage(getLanguage()).validate();
    }
}
