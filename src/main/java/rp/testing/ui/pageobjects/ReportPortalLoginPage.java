package rp.testing.ui.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import rp.testing.ui.pageobjects.basepageobjects.ReportPortalPageObject;

public class ReportPortalLoginPage extends ReportPortalPageObject {

    @FindBy(xpath = "//input[@placeholder='Login']")
    private WebElement usernameTextField;
    @FindBy(xpath = "//input[@placeholder='Password']")
    private WebElement passwordTextField;
    @FindBy(xpath = "//button[@class='bigButton__big-button--ivY7j bigButton__rounded-corners--3wKBL bigButton__color-organish--4iYXy' and text() = 'Login']")
    private WebElement loginButton;

    private ReportPortalLoginPage(WebDriver webDriver) {
        super(webDriver);
    }

    public static ReportPortalLoginPage using(WebDriver webDriver) {
        return new ReportPortalLoginPage(webDriver);
    }

    public void login(String username, String password) {
        usernameTextField.sendKeys(username);
        passwordTextField.sendKeys(password);
        loginButton.click();
    }

}
