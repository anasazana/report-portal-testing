package rp.testing.testng.tests.ui.filters;

import org.awaitility.Awaitility;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import rp.testing.testng.tests.ui.filters.basetests.ReportPortalBaseTest;
import rp.testing.ui.constants.FilterParameter;
import rp.testing.ui.constants.FilterParameterCondition;
import rp.testing.ui.pageobjects.ReportPortalLaunchesPage;
import rp.testing.ui.steps.FilteredLaunchesValidator;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static rp.testing.utils.TestGroup.SMOKE;

public class FilterLaunchesByNumberTest extends ReportPortalBaseTest {

    private ReportPortalLaunchesPage launchesPage;

    @BeforeMethod(alwaysRun = true)
    public void initTestingPage() {
        launchesPage = ReportPortalLaunchesPage.using(driver.get())
                .launch()
                .clickAddFilterButton();
    }

    @Test(groups = {SMOKE}, dataProvider = "launchNumberDataProvider",
            description = "Filter launches by number: equal to")
    public void filterByLaunchNumberEquals(String launchNumber) {
        launchesPage.selectFilterParameters(FilterParameter.LAUNCH_NUMBER);
        launchesPage.filterByLaunchNumber(FilterParameterCondition.EQUALS, launchNumber);
        int launchNum = Integer.parseInt(launchNumber);
        boolean isNumberOutOfRange = 1 > launchNum || launchNum > 5;
        List<WebElement> filteredLaunches = getFilteredLaunches(isNumberOutOfRange ? 0 : 1);
        FilteredLaunchesValidator.validateLaunchNumbers(
                FilterParameterCondition.EQUALS,
                filteredLaunches,
                launchNumber
        );
    }

    @Test(groups = {SMOKE}, dataProvider = "launchNumberDataProvider",
            description = "Filter launches by number: less than or equal to")
    public void filterByLaunchNumberLessThanOrEquals(String launchNumber) {
        launchesPage.selectFilterParameters(FilterParameter.LAUNCH_NUMBER);
        launchesPage.filterByLaunchNumber(FilterParameterCondition.LESS_THAN_OR_EQUAL, launchNumber);
        int launchNum = Integer.parseInt(launchNumber);
        int expectedNumberOfLaunches = Math.min(launchNum, 5);
        List<WebElement> filteredLaunches = getFilteredLaunches(expectedNumberOfLaunches);
        FilteredLaunchesValidator.validateLaunchNumbers(
                FilterParameterCondition.LESS_THAN_OR_EQUAL,
                filteredLaunches,
                launchNumber
        );
    }

    @Test(groups = {SMOKE}, dataProvider = "launchNumberWithExpectedResultDataProvider",
            description = "Filter launches by number: greater than or equal to")
    public void filterByLaunchNumberGreaterThanOrEquals(String launchNumber, String expectedNumberOfLaunches) {
        launchesPage.selectFilterParameters(FilterParameter.LAUNCH_NUMBER);
        launchesPage.filterByLaunchNumber(FilterParameterCondition.GREATER_THAN_OR_EQUAL, launchNumber);
        int expectedLaunches = Integer.parseInt(expectedNumberOfLaunches);
        List<WebElement> filteredLaunches = getFilteredLaunches(expectedLaunches);
        FilteredLaunchesValidator.validateLaunchNumbers(
                FilterParameterCondition.GREATER_THAN_OR_EQUAL,
                filteredLaunches,
                launchNumber
        );
    }

    private List<WebElement> getFilteredLaunches(int expectedNumberOfLaunches) {
        AtomicReference<List<WebElement>> filteredLaunches = new AtomicReference<>();
        Awaitility.await(String.format("There should be %d filtered results", expectedNumberOfLaunches))
                .atMost(60, TimeUnit.SECONDS)
                .until(
                        () -> {
                            List<WebElement> filteredResults = launchesPage.getFilteredResults();
                            if (filteredResults.size() == expectedNumberOfLaunches) {
                                filteredLaunches.set(launchesPage.getFilteredResults());
                                return true;
                            } else {
                                return false;
                            }
                        }
                );
        return filteredLaunches.get();
    }

    @DataProvider
    private Object[][] launchNumberDataProvider() {
        return new Object[][]{{"0"}, {"1"}, {"3"}, {"5"}, {"6"}};
    }

    @DataProvider
    private Object[][] launchNumberWithExpectedResultDataProvider() {
        return new Object[][]{{"0", "5"}, {"1", "5"}, {"3", "3"}, {"5", "1"}, {"6", "0"}};
    }

}
