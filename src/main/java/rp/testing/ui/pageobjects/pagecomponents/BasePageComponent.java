package rp.testing.ui.pageobjects.pagecomponents;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public abstract class BasePageComponent {

    protected final WebDriver webDriver;

    protected BasePageComponent(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }
}
