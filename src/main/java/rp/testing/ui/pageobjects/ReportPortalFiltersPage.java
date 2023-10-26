package rp.testing.ui.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;
import rp.testing.ui.pageobjects.basepageobjects.ReportPortalBasePage;
import rp.testing.utils.TestConfiguration;

import java.util.List;

public class ReportPortalFiltersPage extends ReportPortalBasePage {

    @FindBy(xpath = "//span[@title='Filters']")
    private WebElement headerLabel;
    @FindBy(className = "inputSearch__input--3e4db type-text")
    private WebElement searchField;
    @FindBy(xpath = "//span[@class='ghostButton__text--SjHtK']")
    private WebElement addFilterButton;
    @FindBy(className = "headerCell__title-short--3_s1A")
    private List<WebElement> resultsTableColumns;
    @FindBy(className = "gridRow__grid-row--1pS-5 gridRow__change-mobile--3Kl_V")
    private List<WebElement> filterSearchResults;

    By searchResults = By.className("gridRow__grid-row--1pS-5 gridRow__change-mobile--3Kl_V");

    protected ReportPortalFiltersPage(WebDriver webDriver) {
        super(webDriver);
    }

    public static ReportPortalFiltersPage using(WebDriver webDriver) {
        return new ReportPortalFiltersPage(webDriver);
    }

    public ReportPortalFiltersPage launch() {
        driver.get(TestConfiguration.filtersPageUrl());
        new WebDriverWait(driver, 60).until(
                wd -> ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete")
        );
        return this;
    }

    public String getHeaderLabelText() {
        return headerLabel.getText();
    }

    public void searchByName(String searchByName) {
        searchField.click();
        searchField.sendKeys(searchByName);
        filterSearchResults = driver.findElements(searchResults);
    }

    public List<WebElement> getSearchResults() {
        return filterSearchResults;
    }

    public void clickAddFilterButton() {
        addFilterButton.click();
    }

}
