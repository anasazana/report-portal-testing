package rp.testing.ui.selenide.pageobjects;

import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.WebDriver;

public abstract class ReportPortalPageObject {

    protected final WebDriver driver;

    protected ReportPortalPageObject(WebDriver driver) {
        this.driver = driver;
        WebDriverRunner.setWebDriver(driver);
    }
}
