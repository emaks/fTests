package com.test.qa.search;

import com.test.qa.bookmarks.pages.BookmarksPage;
import com.test.qa.search.pages.SearchPage;
import com.test.qa.search.pages.SearchResultPage;
import com.test.qa.utils.TestRunner;
import org.junit.Test;

import java.util.List;

public class SearchTest extends TestRunner {
    @Test
    public void searchItemsAndAddToBookmarks() {
        //Test data
        String adGroup = "Электротехника";
        String searchPhrase = "Computer";
        String adType = "Продажа";
        String fromPrice = "160";
        String toPrice = "300";
        int favoritesCount = 3;
        //Steps
        mainPage.changeLanguage("ru");
        SearchPage searchPage = mainPage.openAdGroup(adGroup).openSearch();

        SearchResultPage searchResultPage = searchPage
                .fillSearchPhrase(searchPhrase)
                .submitSearch();
        searchResultPage.sortByPrice();
        searchResultPage.selectAdType(adType);
        searchResultPage.openAdvancedSearch();

        searchResultPage = searchPage
                .validate()
                .fillPriceRange(fromPrice, toPrice)
                .submitSearch();
        List<String> selectedAds = searchResultPage.chooseRandomAds(favoritesCount);
        searchResultPage.addToBookmarks();

        BookmarksPage bookmarksPage = searchResultPage.openBookmarks();
        bookmarksPage.verifyFavoriteAds(selectedAds);
    }
}
