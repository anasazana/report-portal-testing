package rp.testing.junit.tests.ui.selenide;

import com.codeborne.selenide.Selenide;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import rp.testing.api.client.newclient.ReportPortalFiltersNewClient;
import rp.testing.api.datagenerator.FilterGenerator;
import rp.testing.api.model.filter.EntryCreatedRS;
import rp.testing.api.model.filter.UpdateUserFilterRQ;
import rp.testing.ui.driver.WebDriverFactory;
import rp.testing.ui.selenide.steps.ReportPortalCommonSteps;
import rp.testing.utils.RandomizerUtils;
import rp.testing.utils.TestConfiguration;

@Slf4j
public abstract class ReportPortalBaseTest {

    protected static final ReportPortalFiltersNewClient client = new ReportPortalFiltersNewClient();
    protected static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    protected String testFilterName;

    @BeforeAll
    protected static void initWebDriver() {
        driver.set(WebDriverFactory.createInstance(TestConfiguration.browserName()));
        ReportPortalCommonSteps.login(driver.get());
    }

    @BeforeEach
    public void createTestFilterName() {
        testFilterName = RandomizerUtils.generateTestFilterName();
    }

    @AfterAll
    public static void closeBrowser() {
        Selenide.closeWindow();
        Selenide.webdriver().driver().getWebDriver().quit();
    }

    protected String createTestFilter(String testFilterName) {
        UpdateUserFilterRQ testFilter = FilterGenerator.generateTestFilter();
        testFilter.setName(testFilterName);
        return client.createFilter(testFilter)
                .validateStatusCode(HttpStatus.SC_CREATED)
                .getBodyAsObject(EntryCreatedRS.class)
                .getId();
    }

    protected void deleteTestFilter(String testFilterId) {
        client.deleteFilterById(testFilterId);
    }

}
