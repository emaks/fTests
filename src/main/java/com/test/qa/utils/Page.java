package com.test.qa.utils;

import org.hamcrest.Matchers;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;

import static org.hamcrest.MatcherAssert.assertThat;

public abstract class Page<T> {
    protected final WebDriver driver;
    private String mca;
    private String title;
    private String language = "";

    public Page(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(this.driver)), this);
    }

    protected void setMca(String value) {
        mca = value;
    }

    public T setLanguage(String value) {
        language = value;
        //noinspection unchecked
        return (T) this;
    }

    public String getTitle() {
        return title;
    }

    protected void setTitle(String value) {
        title = value;
    }

    public String getLanguage() {
        return language;
    }

    public T open() {
        driver.get(getUrl());
        validate();
        //noinspection unchecked
        return (T) this;
    }

    public T validate() {
        new WebDriverWait(driver, 10).until(ExpectedConditions.titleIs(getTitle()));
        String actual = driver.getCurrentUrl();
        String query = Utils.getUri(actual).getQuery();
        if (query != null) {
            actual = actual.replace(query, "");
        }
        assertThat("Opened page has wrong url", getUrl(), Matchers.equalTo(actual));
        //noinspection unchecked
        return (T) this;
    }

    protected String getUrl() {
        return String.format(
                "%s%s%s",
                PropertyLoader.loadProperty("site.url"),
                language.isEmpty() ? "" : language + "/",
                mca
        );
    }
}
