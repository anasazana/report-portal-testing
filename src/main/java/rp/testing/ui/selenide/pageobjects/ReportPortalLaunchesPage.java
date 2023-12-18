package rp.testing.ui.selenide.pageobjects;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import rp.testing.ui.constants.FilterParameter;
import rp.testing.ui.constants.FilterParameterCondition;
import rp.testing.utils.TestConfiguration;
import rp.testing.utils.WaiterUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byClassName;
import static com.codeborne.selenide.Selectors.byTagName;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static rp.testing.utils.WaiterUtils.TIMEOUT_30_S;

public class ReportPortalLaunchesPage extends ReportPortalPageObject {

    private final By addFilter = byText("Add filter");
    private final By moreFilterParamsDropdownButton = byText("More");
    private final By saveFilter = byText("Save");
    private final By cloneFilter = byText("Clone");
    private final By displayedFilters = byClassName("filterItem__filter-item--d7jvJ");
    private final By displayedFilterName = byClassName("filterItem__name--jHeQX");
    private final By selectedFilterParameters = byClassName("entitiesGroup__entity-item--iVJ16");
    private final By selectedParameterNameLabel = byTagName("span");
    private final By selectedParameterConditionToggle = byClassName("inputConditional__conditions-selector--BIH6U");
    private final By launchNumberTextField = byXpath("//input[@class='inputConditional__input--esNOk " +
            "inputConditional__touched--XfJZK' and @placeholder='Enter number']");
    private final By filteredLaunches = byClassName("gridRow__grid-row-wrapper--xj8DG");
    private SelenideElement filterParametersDropdownButton;

    public ReportPortalLaunchesPage(WebDriver webDriver) {
        super(webDriver);
    }

    public static ReportPortalLaunchesPage using(WebDriver webDriver) {
        return new ReportPortalLaunchesPage(webDriver);
    }

    public ReportPortalLaunchesPage launch() {
        Selenide.open(TestConfiguration.launchesPageUrl());
        WaiterUtils.waitForPageToBeLoadedJs(driver, TIMEOUT_30_S);
        WaiterUtils.pause(1000);
        return this;
    }

    public ReportPortalLaunchesPage clickAddFilterButton() {
        $(addFilter).shouldBe(visible, TIMEOUT_30_S).click();
        filterParametersDropdownButton = $(moreFilterParamsDropdownButton).shouldBe(visible, TIMEOUT_30_S);
        return this;
    }

    public ReportPortalEditFilterComponent saveFilter() {
        $(saveFilter).shouldBe(visible).click();
        return ReportPortalEditFilterComponent.using(driver);
    }

    public ReportPortalLaunchesPage cloneFilter(String filterName) {
        SelenideElement filter = getDisplayedFilters().stream().filter(f ->
            f.find(displayedFilterName).getText().equals(filterName)
        ).findFirst().get();
        filter.shouldBe(visible).click();
        $(cloneFilter).shouldBe(visible).click();
        return this;
    }

    public List<SelenideElement> getDisplayedFilters() {
        try {
            return $$(displayedFilters).asFixedIterable().stream().collect(Collectors.toList());
        } catch (NoSuchElementException e) {
            return new ArrayList<>();
        }
    }

    public ReportPortalLaunchesPage selectFilterParameters(String... filterParameterNames) {
        for (String filterParameterName : filterParameterNames) {
            filterParametersDropdownButton.shouldBe(visible, TIMEOUT_30_S).click();
            Selenide.actions()
                    .moveToElement($(byText(filterParameterName)))
                    .click().build().perform();
        }
        return this;
    }

    public ReportPortalLaunchesPage filterByLaunchNumber(FilterParameterCondition condition, int launchNumber) {
        ElementsCollection filters = $$(selectedFilterParameters);
        SelenideElement launchNumberFilter = filters.asFixedIterable().stream()
                .filter(filterItem -> filterItem
                        .findElement(selectedParameterNameLabel)
                        .getText()
                        .equals(FilterParameter.LAUNCH_NUMBER)
                ).findFirst().get();

        launchNumberFilter.find(selectedParameterConditionToggle).shouldBe(visible, TIMEOUT_30_S).click();
        launchNumberFilter.find(byText(condition.getDropdownName())).click();
        launchNumberFilter.findElement(launchNumberTextField).sendKeys(Integer.toString(launchNumber));

        return this;
    }

    public List<SelenideElement> getFilteredResults() {
        WebDriverRunner.setWebDriver(driver);
        return $$(filteredLaunches).asFixedIterable().stream().collect(Collectors.toList());
    }

}
