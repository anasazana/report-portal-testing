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
import rp.testing.ui.constants.FilterParameter;
import rp.testing.ui.constants.FilterParameterCondition;
import rp.testing.ui.selenide.pageobjects.ReportPortalFiltersPage;
import rp.testing.ui.selenide.pageobjects.ReportPortalLaunchesPage;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Execution(ExecutionMode.CONCURRENT)
@ExtendWith({ScreenShooterExtension.class})
public class AddFilterTest extends ReportPortalBaseTest {
    private ReportPortalLaunchesPage launchesPage;
    private ReportPortalFiltersPage filtersPage;

    @BeforeEach
    public void initTestingPages() {
        launchesPage = ReportPortalLaunchesPage.using(driver.get()).launch();
        filtersPage = ReportPortalFiltersPage.using(driver.get());
    }

    @AfterEach
    public void cleanUp() {
        filtersPage.launch().deleteFilterByName(testFilterName);
    }

    @Test
    @DisplayName("Add a filter")
    public void filterCanBeAdded() {
        launchesPage.clickAddFilterButton()
                .selectFilterParameters(FilterParameter.LAUNCH_NUMBER)
                .filterByLaunchNumber(FilterParameterCondition.EQUALS, 1)
                .saveFilter()
                .setName(testFilterName)
                .save();
        Assertions.assertNotNull(filtersPage.launch().searchFilterByName(testFilterName));
    }

}
