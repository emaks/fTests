package com.test.qa.utils;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
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
        return getDriver(capabilities.get());
    }

    public static WebDriver getDriver(Capabilities capabilities) {
        String gridHubUrl = PropertyLoader.loadProperty("grid.url");
        if ("".equals(gridHubUrl)) {
            gridHubUrl = null;
        }
        if (capabilities.getBrowserName().equals("chrome")) {
            ChromeDriverManager.getInstance().setup();
        } else if (capabilities.getBrowserName().equals("firefox")) {
            FirefoxDriverManager.getInstance().setup();
        }
        return WebDriverFactory.getDriver(gridHubUrl, capabilities);
    }

    public static void dismissDriver(WebDriver driver) {
        try {
            WebDriverFactory.dismissDriver(driver);
        } finally {
            capabilities.remove();
        }
    }
}
