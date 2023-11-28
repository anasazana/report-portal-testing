package rp.testing.junit.tests.ui.basetests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import rp.testing.ui.driver.WebDriverFactory;
import rp.testing.ui.steps.ReportPortalCommonSteps;
import rp.testing.utils.TestConfiguration;

public abstract class ReportPortalBaseTest {

    protected final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    @BeforeEach
    protected void initWebDriver() {
        driver.set(WebDriverFactory.createInstance(TestConfiguration.browserName()));
        ReportPortalCommonSteps.login(driver.get());
    }

    @AfterEach
    public void closeBrowser() {
        driver.get().quit();
    }

}
