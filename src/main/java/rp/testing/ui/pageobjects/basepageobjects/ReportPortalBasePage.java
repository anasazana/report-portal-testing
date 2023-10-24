package rp.testing.ui.pageobjects.basepageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public abstract class ReportPortalBasePage extends ReportPortalPageObject {

    @FindBy(className = "sidebarButton__sidebar-nav-btn--1prEO")
    private List<WebElement> leftSideMenuItems;
    @FindBy(className = "sidebar__bottom-block--3EH2A")
    private WebElement userMenuButton;
    @FindBy(className = "userBlock__menu-item--3VBsZ")
    private WebElement userMenuItems;

    protected ReportPortalBasePage(WebDriver webDriver) {
        super(webDriver);
    }

    public void selectFromLeftSideMenu(String menuItemName) {

    }
}
