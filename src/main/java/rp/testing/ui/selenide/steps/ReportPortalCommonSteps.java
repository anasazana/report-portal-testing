package rp.testing.ui.selenide.steps;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import rp.testing.ui.selenide.pageobjects.ReportPortalLoginPage;
import rp.testing.utils.TestConfiguration;

import static rp.testing.utils.TestConfiguration.password;
import static rp.testing.utils.TestConfiguration.username;

@Slf4j
@UtilityClass
public class ReportPortalCommonSteps {

    public static void login(WebDriver driver) {
        loginAs(driver, username(), password());
    }

    public static void loginAs(WebDriver driver, String username, String password) {
        ReportPortalLoginPage loginPage = ReportPortalLoginPage.using(driver).launch();
        if (!loginPage.isLoggedIn()) {
            log.info("Login as {} user", TestConfiguration.username());
            loginPage.login(username, password);
        }
    }

}
