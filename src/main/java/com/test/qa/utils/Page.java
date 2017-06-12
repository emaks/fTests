package com.test.qa.utils;

import org.hamcrest.Matchers;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import ru.yandex.qatools.allure.annotations.Step;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;

import static org.hamcrest.MatcherAssert.assertThat;

public abstract class Page<T> {
    protected WebDriver driver;
    private String mca;
    private String title;

    public Page(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(this.driver)), this);
    }

    protected void setMca(String value) {
        mca = value;
    }

    protected void setTitle(String value) {
        title = value;
    }

    @Step("Open page")
    public T open() {
        driver.get(getUrl());
        return validate();
    }

    public T validate() {
        validateUrl();
        validateTitle();
        return (T) this;
    }

    private void validateUrl() {
        assertThat("Opened page has wrong url", driver.getCurrentUrl(), Matchers.equalTo(getUrl()));
    }

    private void validateTitle() {
        assertThat("Opened page has wrong title", driver.getTitle(), Matchers.equalTo(title));
    }

    protected String getUrl() {
        return PropertyLoader.loadProperty("site.url") + mca;
    }

    public WebDriver getDriver() {
        return this.driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }
}
