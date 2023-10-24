package rp.testing.ui.pageobjects.basepageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public abstract class ReportPortalPageObject {

    protected final WebDriver webDriver;

    protected ReportPortalPageObject(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }
}
