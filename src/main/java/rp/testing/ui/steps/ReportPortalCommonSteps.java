package rp.testing.ui.steps;

import lombok.experimental.UtilityClass;
import org.openqa.selenium.WebDriver;
import rp.testing.ui.pageobjects.ReportPortalLoginPage;

import static rp.testing.utils.TestConfiguration.password;
import static rp.testing.utils.TestConfiguration.username;

@UtilityClass
public class ReportPortalCommonSteps {

    public static void login(WebDriver webDriver) {
        loginAs(webDriver, username(), password());
    }

    public static void loginAs(WebDriver webDriver, String username, String password) {
        ReportPortalLoginPage
                .using(webDriver)
                .launch()
                .login(username, password);
    }

}
