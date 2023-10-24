package rp.testing.ui.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import rp.testing.ui.pageobjects.basepageobjects.ReportPortalBasePage;
import rp.testing.ui.pageobjects.pagecomponents.FilterEditorWindow;

import java.util.Arrays;
import java.util.List;

public class ReportPortalLaunchesPage extends ReportPortalBasePage {

    @FindBy(className = "ghostButton__ghost-button--1PhF7 ghostButton__color-topaz--2GTla with-icon ghostButton__filled-icon--bHBq5 ghostButton__mobile-minified--1m7Pj")
    private WebElement addFilterButton;
    @FindBy(xpath="//span[text()='Discard']")
    private WebElement discardButton;
    @FindBy(xpath="//span[text()='Clone']")
    private WebElement cloneButton;
    @FindBy(xpath="//span[text()='Edit']")
    private WebElement editButton;
    @FindBy(xpath="//span[text()='Save']")
    private WebElement saveButton;
    @FindBy(className = "expandToggler__expand-toggler--2WJih")
    private WebElement criteriaVisibilityToggle;
    @FindBy(className = "entitiesSelector__toggler--3ArsT")
    private WebElement filterParametersDropdownButton;
    @FindBy(className = "inputDropdownSorting__dropdown--28Xzv")
    private WebElement sortingDropdownButton;
    @FindBy(className = "filterList__item--3-v3N")
    private List<WebElement> displayedFiltersList;
    @FindBy(className = "fieldFilterEntity__field-filter-entity--1dlYg")
    private List<WebElement> selectedFilterParameters;
    @FindBy(className = "gridRow__grid-row-wrapper--1dI9K")
    private List<WebElement> filteredResults;

    private List<WebElement> filterParametersDropdownItems;
    private List<WebElement> sortingDropdownItems;
    private FilterEditorWindow filterEditor;

    private final By filterParameters = By.className("entitiesSelector__entity-item--1hjxE");
    private final By filterParameterName = By.className("inputCheckbox__children-container--1lo3t");
    private final By sortingAttributes = By.className("inputDropdownSortingOption__single-option--20p-6");

    protected ReportPortalLaunchesPage(WebDriver webDriver) {
        super(webDriver);
    }

    public static ReportPortalLaunchesPage using(WebDriver webDriver) {
        return new ReportPortalLaunchesPage(webDriver);
    }

    public void selectFilterParameters(String... filterParameterNames) {
        filterParametersDropdownButton.click();
        filterParametersDropdownItems = webDriver.findElements(filterParameters);
        List<String> selectedFilterParameters = Arrays.asList(filterParameterNames);
        for (WebElement filterParameter : filterParametersDropdownItems) {
            String parameterName = filterParameter.findElement(filterParameterName).getText();
            if (selectedFilterParameters.contains(parameterName)) {
                filterParameter.click();
                filterParametersDropdownButton.click();
            }
        }
        filterParametersDropdownButton.click();
    }

    public void selectSorting(String sortingAttribute) {
        sortingDropdownButton.click();
        sortingDropdownItems = webDriver.findElements(sortingAttributes);
        for (WebElement sortingDropdownItem : sortingDropdownItems) {
            if (sortingDropdownItem.getText().equals(sortingAttribute)) {
                sortingDropdownItem.click();
                break;
            }
        }
    }

}
