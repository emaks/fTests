package com.test.qa.main.pages;

import com.test.qa.main.blocks.SearchFilter;
import com.test.qa.utils.Page;
import com.test.qa.utils.Utils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.element.Link;

import java.util.List;
import java.util.stream.Collectors;

public class SearchResultsPage extends Page<SearchResultsPage> {
    private SearchFilter searchFilter;
    @FindBy(css = "#progress")
    private HtmlElement loadingProgress;
    private List<Video> videos;

    public SearchResultsPage(WebDriver driver, String searchPhrase) {
        super(driver);
        setMca("results?search_query=" + searchPhrase.replace(" ", "+"));
        setTitle(searchPhrase + " - YouTube");
    }

    public SearchResultsPage sortByRating() {
        searchFilter.expandFilter();
        searchFilter.sortByRating();
        new WebDriverWait(driver, 10).until(Utils.invisibilityOf(loadingProgress));
        return this;
    }

    public List<VideoPage> getVideos(int count) {
        return videos.stream().map(link -> new VideoPage(driver, link.getVideoName(), link.getVideoMca()))
            .limit(count)
            .collect(Collectors.toList());
    }

    @FindBy(css = "[id^='item-section-']>li")
    public static class Video extends HtmlElement {
        @FindBy(css = "h3>a")
        private Link name;

        public String getVideoName() {
            return name.getText();
        }

        public String getVideoMca() {
            return name.getAttribute("href").split("\\?")[1];
        }
    }
}
