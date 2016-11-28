package com.test.qa.utils;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class PropertyLoader {
    private static final String DEBUG_PROPERTIES = "/debug.properties";

    public static Capabilities loadCapabilities() {
        try {
            return loadCapabilities(System.getProperty("application.properties", DEBUG_PROPERTIES));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Capabilities loadCapabilities(String fromResource) throws IOException {
        String capabilitiesFile = loadProperty("capabilities", fromResource);
        Properties capsProps = new Properties();
        capsProps.load(PropertyLoader.class.getClassLoader().getResourceAsStream(capabilitiesFile));

        DesiredCapabilities capabilities = new DesiredCapabilities();
        for (String name : capsProps.stringPropertyNames()) {
            String value = capsProps.getProperty(name);
            if (value.toLowerCase().equals("true") || value.toLowerCase().equals("false")) {
                capabilities.setCapability(name, Boolean.valueOf(value));
            } else if (value.startsWith("file:")) {
                capabilities.setCapability(name, new File(".", value.substring(5)).getCanonicalFile().getAbsolutePath());
            } else {
                capabilities.setCapability(name, value);
            }
        }

        return capabilities;
    }

    public static String loadProperty(String name) {
        return loadProperty(name, System.getProperty("application.properties", DEBUG_PROPERTIES));
    }

    public static String loadProperty(String name, String fromResource) {
        try {
            Properties props = new Properties();
            props.load(PropertyLoader.class.getClassLoader().getResourceAsStream(fromResource));

            return props.getProperty(name);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
