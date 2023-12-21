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
import rp.testing.utils.WaiterUtils;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Execution(ExecutionMode.CONCURRENT)
@ExtendWith({ScreenShooterExtension.class})
public class CopyFilterTest extends ReportPortalBaseTest {
    private ReportPortalLaunchesPage launchesPage;
    private ReportPortalFiltersPage filtersPage;
    private String testFilterId;
    private String testFilterCopyName;

    @BeforeEach
    public void initTestingPages() {
        testFilterId = createTestFilter(testFilterName);
        testFilterCopyName = "Copy " + testFilterName;
        launchesPage = ReportPortalLaunchesPage.using(driver.get());
        filtersPage = ReportPortalFiltersPage.using(driver.get()).launch();
    }

    @AfterEach
    public void cleanUp() {
        deleteTestFilter(testFilterId);
        filtersPage.launch().deleteFilterByName(testFilterCopyName);
    }

    @Test
    @DisplayName("Clone filter")
    public void filterCanBeCloned() {
        filtersPage.setDisplayFilterOnLaunchesToggle(testFilterName, true);
        launchesPage.launch()
                .cloneFilter(testFilterName)
                .saveFilter()
                .save();
        WaiterUtils.pause();
        Assertions.assertTrue(FiltersValidator.isFilterDisplayed(launchesPage, testFilterCopyName));
    }

}
