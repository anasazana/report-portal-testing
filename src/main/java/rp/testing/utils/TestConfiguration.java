package rp.testing.utils;

import rp.testing.ui.constants.TestRunMode;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestConfiguration {

    private static final Properties PROPERTIES;

    static {
        PROPERTIES = new Properties();
        try (InputStream stream = TestConfiguration.class.getClassLoader()
                .getResourceAsStream("application.properties")) {
            PROPERTIES.load(stream);
        } catch (IOException e) {
            throw new RuntimeException("Configuration file not found.", e);
        }
    }

    public static String browserName() {
        return PROPERTIES.getProperty("browserName").toUpperCase();
    }

    public static String rpBaseUrl() {
        return PROPERTIES.getProperty("rpUrl");
    }

    public static String rpUsername() {
        return PROPERTIES.getProperty("rpUsername");
    }

    public static String rpPassword() {
        return PROPERTIES.getProperty("rpPassword");
    }

    public static String gridUrl() {
        return PROPERTIES.getProperty("gridUrl");
    }

    public static TestRunMode runMode() {
        return TestRunMode.valueOf(PROPERTIES.getProperty("runMode").toUpperCase());
    }

    public static boolean headless() {
        return Boolean.parseBoolean(PROPERTIES.getProperty("headless"));
    }

}
