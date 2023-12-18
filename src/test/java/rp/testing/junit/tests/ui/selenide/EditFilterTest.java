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

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Execution(ExecutionMode.CONCURRENT)
@ExtendWith({ScreenShooterExtension.class})
public class EditFilterTest extends ReportPortalBaseTest {
    private ReportPortalFiltersPage filtersPage;
    private String testFilterId;

    @BeforeEach
    public void initTestingPage() {
        filtersPage = ReportPortalFiltersPage.using(driver.get()).launch();
        testFilterId = createTestFilter(testFilterName);
    }

    @AfterEach
    public void cleanUp() {
        deleteTestFilter(testFilterId);
    }

    @Test
    @DisplayName("Edit a filter")
    public void filterCanBeEdited() {
        String newName = testFilterName + "-updated";
        String newDescription = "Filter description - updated";
        filtersPage.editFilter(testFilterName)
                .setName(newName)
                .setDescription(newDescription)
                .save();
        Assertions.assertEquals(newDescription, filtersPage.getFilterDescription(newName));
    }

}
