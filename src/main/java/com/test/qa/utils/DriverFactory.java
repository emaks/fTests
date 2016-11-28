package com.test.qa.utils;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import ru.stqa.selenium.factory.WebDriverFactory;

public class DriverFactory {
    private static final ThreadLocal<Capabilities> capabilities = new ThreadLocal<Capabilities>() {
        @Override
        protected synchronized Capabilities initialValue() {
            return PropertyLoader.loadCapabilities();
        }
    };

    public static WebDriver getDriver() {
        String gridHubUrl = PropertyLoader.loadProperty("grid.url");
        if ("".equals(gridHubUrl)) {
            gridHubUrl = null;
        }
        return WebDriverFactory.getDriver(gridHubUrl, capabilities.get());
    }

    public static void dismissDriver(WebDriver driver) {
        try {
            WebDriverFactory.dismissDriver(driver);
        } finally {
            capabilities.remove();
        }
    }
}
