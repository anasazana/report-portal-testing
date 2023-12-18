package rp.testing.ui.selenide.pageobjects;

import com.codeborne.selenide.Selenide;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import rp.testing.utils.TestConfiguration;
import rp.testing.utils.WaiterUtils;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static rp.testing.utils.WaiterUtils.TIMEOUT_30_S;

public class ReportPortalLoginPage extends ReportPortalPageObject {

    private final By usernameTextField = By.xpath(
            "//input[@class='inputOutside__input--Ad7Xu' and @name='login']"
    );
    private final By passwordTextField = By.xpath(
            "//input[@class='inputOutside__input--Ad7Xu' and @name='password']"
    );
    private final By loginButton = By.xpath("//button[text() = 'Login']");

    private ReportPortalLoginPage(WebDriver webDriver) {
        super(webDriver);
    }

    public static ReportPortalLoginPage using(WebDriver webDriver) {
        return new ReportPortalLoginPage(webDriver);
    }

    public ReportPortalLoginPage launch() {
        Selenide.open(TestConfiguration.baseUrl());
        WaiterUtils.waitForPageToBeLoadedJs(driver, TIMEOUT_30_S);
        WaiterUtils.pause();
        return this;
    }

    public void login(String username, String password) {
        $(usernameTextField).shouldBe(visible).setValue(username);
        $(passwordTextField).shouldBe(visible).setValue(password);
        $(loginButton).shouldBe(visible).click();
    }

    public boolean isLoggedIn() {
        try {
            $(loginButton).shouldBe(visible);
        } catch (Throwable e) {
            return true;
        }
        return false;
    }

}
