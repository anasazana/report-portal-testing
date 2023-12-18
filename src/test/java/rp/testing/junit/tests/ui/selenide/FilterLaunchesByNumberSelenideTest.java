package rp.testing.junit.tests.ui.selenide;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.junit5.ScreenShooterExtension;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import rp.testing.ui.constants.FilterParameter;
import rp.testing.ui.constants.FilterParameterCondition;
import rp.testing.ui.selenide.pageobjects.ReportPortalLaunchesPage;
import rp.testing.ui.selenide.steps.FilteredLaunchesValidator;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static com.codeborne.selenide.Condition.visible;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Execution(ExecutionMode.CONCURRENT)
@ExtendWith({ScreenShooterExtension.class})
public class FilterLaunchesByNumberSelenideTest extends ReportPortalBaseTest {

    private ReportPortalLaunchesPage launchesPage;

    @BeforeEach
    public void initTestingPage() {
        launchesPage = ReportPortalLaunchesPage.using(driver.get()).launch()
                .clickAddFilterButton();
    }

    @ParameterizedTest(name = "Filter launches that are equal to {0}")
    @DisplayName("Filter launches by number: equal to")
    @ValueSource(ints = {0, 1, 3, 5, 6})
    public void  filterByLaunchNumberEquals(int launchNumber) {
        launchesPage.selectFilterParameters(FilterParameter.LAUNCH_NUMBER);
        launchesPage.filterByLaunchNumber(FilterParameterCondition.EQUALS, launchNumber);
        boolean isNumberOutOfRange = 1 > launchNumber || launchNumber > 5;
        List<SelenideElement> filteredLaunches = getFilteredLaunches(isNumberOutOfRange ? 0 : 1);
        FilteredLaunchesValidator.validateLaunchNumbers(
                FilterParameterCondition.EQUALS,
                filteredLaunches,
                launchNumber
        );
    }


    @ParameterizedTest(name = "Filter launches that are less than or equal to {0}")
    @DisplayName("Filter launches by number: less than or equal to")
    @ValueSource(ints = {0, 1, 3, 5, 6})
    public void filterByLaunchNumberLessThanOrEquals(int launchNumber) {
        launchesPage.selectFilterParameters(FilterParameter.LAUNCH_NUMBER);
        launchesPage.filterByLaunchNumber(FilterParameterCondition.LESS_THAN_OR_EQUAL, launchNumber);
        int expectedNumberOfLaunches = Math.min(launchNumber, 5);
        List<SelenideElement> filteredLaunches = getFilteredLaunches(expectedNumberOfLaunches);
        FilteredLaunchesValidator.validateLaunchNumbers(
                FilterParameterCondition.LESS_THAN_OR_EQUAL,
                filteredLaunches,
                launchNumber
        );
    }


    @ParameterizedTest(name = "Filter launches that are greater than or equal to {0}")
    @DisplayName("Filter launches by number: greater than or equal to")
    @CsvSource(value = {"0, 5", "1, 5", "3, 3", "5, 1", "6, 0"})
    public void filterByLaunchNumberGreaterThanOrEquals(int launchNumber, int expectedNumberOfLaunches) {
        launchesPage.selectFilterParameters(FilterParameter.LAUNCH_NUMBER);
        launchesPage.filterByLaunchNumber(FilterParameterCondition.GREATER_THAN_OR_EQUAL, launchNumber);
        List<SelenideElement> filteredLaunches = getFilteredLaunches(expectedNumberOfLaunches);
        FilteredLaunchesValidator.validateLaunchNumbers(
                FilterParameterCondition.GREATER_THAN_OR_EQUAL,
                filteredLaunches,
                launchNumber
        );
    }

    private List<SelenideElement> getFilteredLaunches(int expectedNumberOfLaunches) {
        AtomicReference<List<SelenideElement>> filteredLaunches = new AtomicReference<>();

        Awaitility.await(String.format("There should be %d filtered results", expectedNumberOfLaunches))
                .atMost(30, TimeUnit.SECONDS)
                .pollInterval(100, TimeUnit.MILLISECONDS)
                .until(
                        () -> {
                            List<SelenideElement> filteredResults = launchesPage.getFilteredResults();
                            if (filteredResults.size() == expectedNumberOfLaunches) {
                                filteredLaunches.set(launchesPage.getFilteredResults());
                                return true;
                            } else {
                                return false;
                            }
                        }
                );

        int resultsNumber = filteredLaunches.get().size();
        if (resultsNumber > 3) {
            Selenide.actions()
                    .scrollToElement(filteredLaunches.get().get(resultsNumber - 1))
                    .build().perform();
            filteredLaunches.get().get(resultsNumber - 1).shouldBe(visible, Duration.ofSeconds(3));
        }

        return filteredLaunches.get();
    }

}
