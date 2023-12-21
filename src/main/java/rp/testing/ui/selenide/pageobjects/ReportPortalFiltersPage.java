package rp.testing.ui.selenide.pageobjects;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import rp.testing.utils.TestConfiguration;
import rp.testing.utils.WaiterUtils;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;
import static rp.testing.utils.WaiterUtils.TIMEOUT_30_S;

public class ReportPortalFiltersPage extends ReportPortalPageObject {

    private final By searchField = byXpath("//input[@placeholder='Search by name']");
    private final By searchFilterResult = By.className("gridRow__grid-row-wrapper--xj8DG");
    private final By filterName = By.className("filterName__name-wrapper--nfl7l");
    private final By editFilterIcon = By.className("filterName__pencil-icon--yaU2H");
    private final By filterDescription = By.xpath("//div[@class='markdownViewer__viewer-wrapper--R0IGr']/div/p");
    private final By displayToggle = By.className("displayFilter__switcher-label--gCpT3");
    private final By deleteFilterIcon = By.className("deleteFilterButton__bin-icon--TN0X3");
    private final By deleteFilterButton = By.xpath("//button[text()='Delete']");

    public ReportPortalFiltersPage(WebDriver driver) {
        super(driver);
    }

    public static ReportPortalFiltersPage using(WebDriver webDriver) {
        return new ReportPortalFiltersPage(webDriver);
    }

    public ReportPortalFiltersPage launch() {
        Selenide.open(TestConfiguration.filtersPageUrl());
        WaiterUtils.waitForPageToBeLoadedJs(driver, TIMEOUT_30_S);
        return this;
    }

    public SelenideElement searchFilterByName(String filterName) {
        $(searchField).shouldBe(visible, TIMEOUT_30_S).setValue(filterName);
        WaiterUtils.pause(500);
        try {
            return $(searchFilterResult).shouldBe(visible);
        } catch (ElementNotFound e) {
            return null;
        }
    }

    public void deleteFilterByName(String filterName) {
        SelenideElement filterToDelete = searchFilterByName(filterName);
        filterToDelete.find(deleteFilterIcon).shouldBe(visible, TIMEOUT_30_S).click();
        $(deleteFilterButton).shouldBe(visible).click();
    }

    public ReportPortalEditFilterComponent editFilter(String name) {
        SelenideElement filterToEdit = searchFilterByName(name);
        Selenide.actions()
                .moveToElement(filterToEdit.find(filterName))
                .moveToElement(filterToEdit.find(editFilterIcon))
                .click().build().perform();
        return ReportPortalEditFilterComponent.using(driver);
    }

    public String getFilterDescription(String filterName) {
        return searchFilterByName(filterName).findElement(filterDescription).getText();
    }

    public void setDisplayFilterOnLaunchesToggle(String filterName, boolean isEnabled) {
        SelenideElement filterToDisplay = searchFilterByName(filterName).find(displayToggle);
        boolean filterIsDisplayed = filterToDisplay.getText().equals("ON");
        if (isEnabled != filterIsDisplayed) {
            filterToDisplay.click();
        }
    }

}
