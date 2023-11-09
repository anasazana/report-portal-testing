package rp.testing.bdd;

import org.openqa.selenium.WebDriver;
import rp.testing.ui.driver.WebDriverFactory;
import rp.testing.utils.TestConfiguration;

public class ReportPortalBddConfiguration {

    private static final ThreadLocal<WebDriver> DRIVER;

    static {
        DRIVER = new ThreadLocal<>();
    }

    public static WebDriver getDriver() {
        if (DRIVER.get() == null) {
            DRIVER.set(WebDriverFactory.createInstance(TestConfiguration.browserName()));
        }
        return DRIVER.get();
    }

}
