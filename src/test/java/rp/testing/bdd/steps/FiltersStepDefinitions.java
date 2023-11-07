package rp.testing.bdd.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rp.testing.ui.constants.FilterParameterCondition;
import rp.testing.ui.pageobjects.ReportPortalLaunchesPage;
import rp.testing.ui.steps.FilteredLaunchesValidator;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static rp.testing.bdd.ReportPortalBddConfiguration.getDriver;

public class FiltersStepDefinitions {

    private static final Logger logger = LoggerFactory.getLogger(FiltersStepDefinitions.class);
    private final ReportPortalLaunchesPage launchesPage;

    public FiltersStepDefinitions() {
        launchesPage = ReportPortalLaunchesPage.using(getDriver())
                .launch()
                .clickAddFilterButton();
    }

    @Given("I select {string} filter parameter")
    public void iSelectFilterParameter(String parameterName) {
        logger.info("Selecting {} filter parameter", parameterName);
        launchesPage.selectFilterParameters(parameterName);
    }

    @And("I set Launch number filter parameter condition to {string} and number to {int}")
    public void iSetLaunchNumberFilterParameterConditionAndNumber(String conditionName, int number) {
        logger.info("Setting launch number filter parameter condition to {} and number to {}", conditionName, number);
        launchesPage.filterByLaunchNumber(
                FilterParameterCondition.getByName(conditionName),
                number
        );
    }

    @Then("Filtered results should contain {int} launches where number {string} {int}")
    public void filteredResultsShouldContainLaunchesWhereNumber(int numberOfFilteredResults, String conditionName,
                                                                int number) {
        logger.info("Check that filtered results contain {} launches", numberOfFilteredResults);
        AtomicReference<List<WebElement>> filteredResults = new AtomicReference<>();
        Awaitility.await()
                .atMost(Duration.ofSeconds(30))
                .untilAsserted(() -> {
                    filteredResults.set(launchesPage.getFilteredResults());
                    Assertions.assertEquals(numberOfFilteredResults, filteredResults.get().size());
                });
        logger.info("Check that filtered launches' numbers {} {}", conditionName, number);
        FilteredLaunchesValidator.validateLaunchNumbers(
                FilterParameterCondition.getByName(conditionName),
                launchesPage.getFilteredResults(),
                number
        );
    }
}
