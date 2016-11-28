package com.test.qa.bookmarks.pages;

import com.test.qa.advertisement.blocks.AdListItem;
import com.test.qa.utils.Page;
import org.hamcrest.Matchers;
import org.openqa.selenium.WebDriver;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;

public class BookmarksPage extends Page<BookmarksPage> {
    private List<AdListItem> ads;

    public BookmarksPage(WebDriver driver) {
        super(driver);
        setMca("favorites/");
        setTitle("Объявления - SS.LV - Закладки, Цены - Объявления");
    }

    @Step("Verify that favorite ads have id equal to {0}")
    public void verifyFavoriteAds(List<String> adIds) {
        assertThat("Wrong ads count", ads, Matchers.hasSize(Matchers.equalTo(adIds.size())));
        List<String> actualIds = ads.stream().map(AdListItem::getAdId).collect(Collectors.toList());
        assertThat("Wrong ad id's", actualIds.containsAll(adIds));
    }
}
