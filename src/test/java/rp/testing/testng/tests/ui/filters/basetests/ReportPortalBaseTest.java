package rp.testing.testng.tests.ui.filters.basetests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import rp.testing.ui.driver.WebDriverFactory;
import rp.testing.ui.steps.ReportPortalCommonSteps;
import rp.testing.utils.TestConfiguration;

public abstract class ReportPortalBaseTest {

    protected final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    @BeforeMethod(alwaysRun = true)
    protected void init() {
        driver.set(WebDriverFactory.createInstance(TestConfiguration.browserName()));
        ReportPortalCommonSteps.login(driver.get());
    }

    @AfterMethod(alwaysRun = true)
    public void closeBrowser() {
        driver.get().quit();
    }

}
