package com.test.qa.utils;

import com.test.qa.main.pages.MainPage;
import org.junit.Rule;
import org.junit.rules.ExternalResource;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.RemoteWebDriver;
import ru.yandex.qatools.allure.annotations.Attachment;

import java.util.LinkedHashSet;
import java.util.Set;

public class TestRunner {
    protected MainPage mainPage;
    private WebDriver webDriver;
    @Rule
    public ExternalResource setUp = new ExternalResource() {
        @Override
        protected void before() throws Throwable {
            webDriver = DriverFactory.getDriver();
            webDriver.manage().window().maximize();
            mainPage = new MainPage(webDriver).open();
        }
    };

    @Rule
    public TestWatcher testWatcher = new TestWatcher() {
        protected void failed(Throwable e, Description description) {
            if (webDriver == null) {
                return;
            }
            Set<String> handles = new LinkedHashSet<>();
            try {
                handles = webDriver.getWindowHandles();
            } catch (WebDriverException ignored) {
            }
            for (String handle : handles) {
                try {
                    webDriver.switchTo().window(handle);
                    makeScreenshot(description.getDisplayName().replaceAll("\\d+: ", ""));
                } catch (WebDriverException ignored) {
                }
            }
            dismissDriver();
        }

        protected void succeeded(Description description) {
            if (webDriver == null) {
                return;
            }
            try {
                webDriver.manage().deleteAllCookies();
                webDriver.get("data:,");//Open blank page after deleting cookies
            } catch (WebDriverException e) {
                dismissDriver();
            }
        }

        @SuppressWarnings({"UnusedParameters", "UnusedReturnValue"})
        @Attachment(value = "{0}", type = "image/png")
        private byte[] makeScreenshot(String name) {
            return ((RemoteWebDriver) webDriver).getScreenshotAs(OutputType.BYTES);
        }

        private void dismissDriver() {
            try {
                DriverFactory.dismissDriver(webDriver);
            } finally {
                webDriver = null;
            }
        }
    };
}
