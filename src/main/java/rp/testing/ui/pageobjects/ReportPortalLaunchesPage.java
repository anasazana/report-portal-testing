package rp.testing.ui.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import rp.testing.ui.constants.FilterParameter;
import rp.testing.ui.constants.FilterParameterCondition;
import rp.testing.ui.pageobjects.basepageobjects.ReportPortalBasePage;
import rp.testing.utils.TestConfiguration;
import rp.testing.utils.WaiterUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ReportPortalLaunchesPage extends ReportPortalBasePage {

    @FindBy(xpath = "//div[@class='launchFiltersToolbar__add-filter-button--Hgtlm']/button")
    private WebElement addFilterButton;
    @FindBy(className = "entitiesSelector__toggler--_p1QT")
    private WebElement filterParametersDropdownButton;
    @FindBy(className = "entitiesSelector__entity-item--HmWgJ")
    private List<WebElement> parametersDropdown;
    @FindBy(className = "entitiesGroup__entity-item--iVJ16")
    private List<WebElement> selectedFilterParameterItems;
    @FindBy(className = "gridRow__grid-row-wrapper--xj8DG")
    private List<WebElement> filteredResults;

    private final By selectedParameterNameLabel = By.tagName("span");
    private final By selectedParameterConditionToggle = By.className("inputConditional__conditions-selector--BIH6U");
    private final By launchNumberTextField = By.xpath("//input[@class='inputConditional__input--esNOk " +
            "inputConditional__touched--XfJZK' and @placeholder='Enter number']");

    protected ReportPortalLaunchesPage(WebDriver webDriver) {
        super(webDriver);
    }

    public static ReportPortalLaunchesPage using(WebDriver webDriver) {
        return new ReportPortalLaunchesPage(webDriver);
    }

    public ReportPortalLaunchesPage launch() {
        driver.navigate().to(TestConfiguration.launchesPageUrl());
        WaiterUtils.waitForPageToBeLoadedJs(driver, WaiterUtils.TIMEOUT_60_S);
        return this;
    }

    public ReportPortalLaunchesPage clickAddFilterButton() {
        new WebDriverWait(driver, WaiterUtils.TIMEOUT_30_S)
                .until(ExpectedConditions.visibilityOf(addFilterButton));
        new Actions(driver)
                .moveToElement(addFilterButton)
                .click(addFilterButton)
                .build().perform();
        return this;
    }

    public void selectFilterParameters(String... filterParameterNames) {
        WaiterUtils.waitForPageToBeLoadedJs(driver, WaiterUtils.TIMEOUT_60_S);
        WaiterUtils.pause();
        for (String filterParameterName : filterParameterNames) {
            new WebDriverWait(driver, WaiterUtils.TIMEOUT_60_S)
                    .until(ExpectedConditions.visibilityOf(filterParametersDropdownButton));
            filterParametersDropdownButton.click();
            WaiterUtils.pause();
            WebElement parameterToSelect = parametersDropdown.stream()
                    .filter(parameter -> parameter
                            .findElement(By.className("inputCheckbox__children-container--R83YO"))
                            .getText()
                            .equals(filterParameterName)
                    ).findFirst().orElseThrow(() ->
                            new NoSuchElementException("Parameter not found: " + filterParameterName)
                    );
            parameterToSelect.click();
            WaiterUtils.pause();
        }
    }

    public ReportPortalLaunchesPage filterByLaunchNumber(FilterParameterCondition condition, int launchNumber) {
        new WebDriverWait(driver, WaiterUtils.TIMEOUT_30_S)
                .until(ExpectedConditions.visibilityOfAllElements(selectedFilterParameterItems));
        WaiterUtils.pause();

        WebElement launchNumberFilter = selectedFilterParameterItems.stream()
                .filter(filterItem -> filterItem
                        .findElement(selectedParameterNameLabel)
                        .getText()
                        .equals(FilterParameter.LAUNCH_NUMBER)
                ).findFirst().orElseThrow(() ->
                        new NoSuchElementException("Parameter not found: " + FilterParameter.LAUNCH_NUMBER)
                );
        WaiterUtils.pause();

        launchNumberFilter.findElement(selectedParameterConditionToggle).click();
        WaiterUtils.pause();

        launchNumberFilter.findElement(By.xpath(String.format("//div[@class='inputConditional__conditions-list--wU03a inputConditional__visible--HwiD9']/div[text()='%s']", condition.getDropdownName()))).click();
        WaiterUtils.pause();

        launchNumberFilter.findElement(launchNumberTextField).sendKeys(Integer.toString(launchNumber));

        return this;
    }

    public List<WebElement> getFilteredResults() {
        driver.switchTo().defaultContent();
        WaiterUtils.waitForPageToBeLoadedJs(driver, WaiterUtils.TIMEOUT_30_S);
        try {
            filteredResults = driver.findElements(By.className("gridRow__grid-row-wrapper--xj8DG"));
            new WebDriverWait(driver, WaiterUtils.TIMEOUT_30_S)
                    .until(ExpectedConditions.visibilityOfAllElements(filteredResults));
        } catch (Exception e) {
            filteredResults = new ArrayList<>();
        }
        return filteredResults;
    }

}
