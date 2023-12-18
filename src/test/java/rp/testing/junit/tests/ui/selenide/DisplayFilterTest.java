package rp.testing.junit.tests.ui.selenide;

import com.codeborne.selenide.junit5.ScreenShooterExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import rp.testing.ui.selenide.pageobjects.ReportPortalFiltersPage;
import rp.testing.ui.selenide.pageobjects.ReportPortalLaunchesPage;
import rp.testing.ui.selenide.steps.FiltersValidator;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Execution(ExecutionMode.CONCURRENT)
@ExtendWith({ScreenShooterExtension.class})
public class DisplayFilterTest extends ReportPortalBaseTest {
    private ReportPortalFiltersPage filtersPage;
    private ReportPortalLaunchesPage launchesPage;
    private String testFilterId;

    @BeforeEach
    public void initTestingPage() {
        testFilterId = createTestFilter(testFilterName);
        filtersPage = ReportPortalFiltersPage.using(driver.get()).launch();
        launchesPage = ReportPortalLaunchesPage.using(driver.get());
    }

    @AfterEach
    public void cleanUp() {
        deleteTestFilter(testFilterId);
    }

    @Test
    @DisplayName("Display filter")
    public void filterCanBeDisplayed() {
        filtersPage.setDisplayFilterOnLaunchesToggle(testFilterName, true);
        Assertions.assertTrue(FiltersValidator.isFilterDisplayed(launchesPage, testFilterName));
    }

    @Test
    @DisplayName("Do not display filter")
    public void filterCanBeNotDisplayed() {
        filtersPage.setDisplayFilterOnLaunchesToggle(testFilterName, false);
        Assertions.assertFalse(FiltersValidator.isFilterDisplayed(launchesPage, testFilterName));
    }

}
