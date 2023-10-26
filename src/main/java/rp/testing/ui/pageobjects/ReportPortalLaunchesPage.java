package rp.testing.ui.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import rp.testing.ui.constants.FilterParameterCondition;
import rp.testing.ui.pageobjects.basepageobjects.ReportPortalBasePage;
import rp.testing.utils.TestConfiguration;
import rp.testing.utils.WaiterUtils;

import java.util.ArrayList;
import java.util.List;

import static rp.testing.ui.constants.FilterParameter.LAUNCH_NUMBER;

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
        driver.get(TestConfiguration.launchesPageUrl());
        WaiterUtils.waitForPageToBeLoadedJs(driver, 60);
        return this;
    }

    public ReportPortalLaunchesPage clickAddFilterButton() {
        new WebDriverWait(driver, 30)
                .until(ExpectedConditions.visibilityOf(addFilterButton));
        new Actions(driver)
                .moveToElement(addFilterButton)
                .click(addFilterButton)
                .build().perform();
        return this;
    }

    public void selectFilterParameters(String... filterParameterNames) {
        WaiterUtils.waitForPageToBeLoadedJs(driver, 60);
        WaiterUtils.pause();
        for (String filterParameterName : filterParameterNames) {
            new WebDriverWait(driver, 60)
                    .until(ExpectedConditions.visibilityOf(filterParametersDropdownButton));
            filterParametersDropdownButton.click();
            WaiterUtils.pause();
            WebElement parameterToSelect = parametersDropdown.stream()
                    .filter(parameter -> parameter
                            .findElement(By.className("inputCheckbox__children-container--R83YO"))
                            .getText()
                            .equals(filterParameterName)
                    ).findFirst().get();
            parameterToSelect.click();
            WaiterUtils.pause();
        }
    }

    public ReportPortalLaunchesPage filterByLaunchNumber(FilterParameterCondition condition, String launchNumber) {
        new WebDriverWait(driver, 30)
                .until(ExpectedConditions.visibilityOfAllElements(selectedFilterParameterItems));
        WaiterUtils.pause();

        WebElement launchNumberFilter = selectedFilterParameterItems.stream()
                .filter(filterItem -> filterItem
                        .findElement(selectedParameterNameLabel)
                        .getText()
                        .equals(LAUNCH_NUMBER)
                ).findFirst().get();
        WaiterUtils.pause();

        launchNumberFilter.findElement(selectedParameterConditionToggle).click();
        WaiterUtils.pause();

        launchNumberFilter.findElement(By.xpath(String.format("//div[@class='inputConditional__conditions-list--wU03a inputConditional__visible--HwiD9']/div[text()='%s']", condition.getDropdownName()))).click();
        WaiterUtils.pause();

        launchNumberFilter.findElement(launchNumberTextField).sendKeys(launchNumber);

        return this;
    }

    public List<WebElement> getFilteredResults() {
        driver.switchTo().defaultContent();
        WaiterUtils.waitForPageToBeLoadedJs(driver, 30);
        try {
            filteredResults = driver.findElements(By.className("gridRow__grid-row-wrapper--xj8DG"));
            new WebDriverWait(driver, 10)
                    .until(ExpectedConditions.visibilityOfAllElements(filteredResults));
        } catch (Exception e) {
            filteredResults = new ArrayList<>();
        }
    }

}
