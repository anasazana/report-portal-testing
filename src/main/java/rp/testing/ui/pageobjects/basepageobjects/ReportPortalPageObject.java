package rp.testing.ui.pageobjects.basepageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public abstract class ReportPortalPageObject {

    protected final WebDriver driver;

    protected ReportPortalPageObject(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
}
