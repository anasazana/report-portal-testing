package rp.testing.ui.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import rp.testing.ui.pageobjects.basepageobjects.ReportPortalPageObject;
import rp.testing.utils.TestConfiguration;
import rp.testing.utils.WaiterUtils;

public class ReportPortalLoginPage extends ReportPortalPageObject {

    @FindBy(xpath = "//input[@class='inputOutside__input--Ad7Xu' and @name='login']")
    private WebElement usernameTextField;
    @FindBy(xpath = "//input[@class='inputOutside__input--Ad7Xu' and @name='password']")
    private WebElement passwordTextField;
    @FindBy(xpath = "//button[text() = 'Login']")
    private WebElement loginButton;

    private ReportPortalLoginPage(WebDriver webDriver) {
        super(webDriver);
    }

    public static ReportPortalLoginPage using(WebDriver webDriver) {
        return new ReportPortalLoginPage(webDriver);
    }

    public ReportPortalLoginPage launch() {
        driver.get(TestConfiguration.baseUrl());
        WaiterUtils.waitForPageToBeLoadedJs(driver, 30);
        WaiterUtils.pause();
        return this;
    }

    public void login(String username, String password) {
        new WebDriverWait(driver, 60)
                .until(ExpectedConditions.visibilityOfAllElements(usernameTextField, passwordTextField, loginButton));
        new Actions(driver)
                .sendKeys(usernameTextField, username)
                .sendKeys(passwordTextField, password)
                .click(loginButton)
                .build().perform();
    }

    public boolean isLoggedIn() {
        try {
            new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOf(loginButton));
        } catch (Exception e) {
            return true;
        }
        return false;
    }

}
