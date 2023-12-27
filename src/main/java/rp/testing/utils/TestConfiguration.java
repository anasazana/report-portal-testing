package rp.testing.utils;

import lombok.experimental.UtilityClass;
import rp.testing.ui.constants.TestRunMode;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@UtilityClass
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

    public static String baseUrl() {
        return PROPERTIES.getProperty("rpUrl");
    }

    public static String username() {
        return System.getProperty("rpUsername", PROPERTIES.getProperty("rpUsername"));
    }

    public static String password() {
        return System.getProperty("rpPassword", PROPERTIES.getProperty("rpPassword"));
    }

    public static String token() {
        return System.getProperty("rpToken", PROPERTIES.getProperty("rpToken"));
    }

    public static String projectName() {
        return PROPERTIES.getProperty("projectName");
    }
    public static String apiBaseUrl() {
        return baseUrl() + "api/v1/";
    }

    public static String projectUrl() {
        return baseUrl() + "ui/#" + projectName();
    }

    public static String launchesPageUrl() {
        return projectUrl() + PROPERTIES.getProperty("launchesUri");
    }

    public static String filtersPageUrl() {
        return projectUrl() + PROPERTIES.getProperty("filtersUri");
    }

    public static String gridUrl() {
        return PROPERTIES.getProperty("gridUrl");
    }

    public static String saucelabsUrl() {
        return PROPERTIES.getProperty("saucelabsUrl");
    }

    public static String saucelabsAccessKey() {
        return System.getProperty("sauce.accessKey", PROPERTIES.getProperty("sauce.accessKey"));
    }

    public static Object saucelabUsername() {
        return PROPERTIES.getProperty("sauce.username");
    }

    public static Object saucelabsBuild() {
        return PROPERTIES.getProperty("sauce.build");
    }

    public static Object saucelabsTestName() {
        return PROPERTIES.getProperty("sauce.name");
    }

    public static Object saucelabsTunnelName() {
        return PROPERTIES.getProperty("sauce.tunnelName");
    }

    public static TestRunMode runMode() {
        return TestRunMode.valueOf(PROPERTIES.getProperty("runMode").toUpperCase());
    }

    public static boolean headless() {
        return Boolean.parseBoolean(PROPERTIES.getProperty("headless"));
    }

}
