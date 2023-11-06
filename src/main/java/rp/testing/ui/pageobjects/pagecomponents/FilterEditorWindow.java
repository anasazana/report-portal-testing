package rp.testing.ui.pageobjects.pagecomponents;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FilterEditorWindow extends BasePageComponent {

    @FindBy(className = "modalHeader__modal-title--2ph9G")
    private WebElement modalHeaderLabel;
    @FindBy(className = "modalHeader__close-modal-icon--1qeNl")
    private WebElement closeButton;
    @FindBy(className = "input__input--3DC8i type-text variant-standard")
    private WebElement filterNameTextField;
    @FindBy(className = " CodeMirror-line ")
    private WebElement descriptionTextField;
    @FindBy(className = "bigButton__big-button--ivY7j bigButton__color-gray-60--2LUP6")
    private WebElement cancelButton;
    @FindBy(className = "bigButton__big-button--ivY7j bigButton__color-booger--2IfQT")
    private WebElement addUpdateButton;

    public FilterEditorWindow(WebDriver webDriver) {
        super(webDriver);
    }

    public void setName(String filterName) {
        filterNameTextField.sendKeys(filterName);
    }

    public void setDescription(String filterDescription) {
        filterNameTextField.sendKeys(filterDescription);
    }

    public void cancel() {
        cancelButton.click();
    }

    public void close() {
        closeButton.click();
    }

    public void save() {
        addUpdateButton.click();
    }

}
