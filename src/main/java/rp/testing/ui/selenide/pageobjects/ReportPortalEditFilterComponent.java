package rp.testing.ui.selenide.pageobjects;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import rp.testing.utils.WaiterUtils;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;

public class ReportPortalEditFilterComponent extends ReportPortalPageObject {

    private final By nameField = byXpath("//input[@placeholder='Enter filter name']");
    private final By descriptionField = byXpath("//div[@class='CodeMirror-code']");
    private final By saveButton = byXpath("//button[@class='bigButton__big-button--BmG4Q " +
            "bigButton__color-booger--EpRlL']");

    private ReportPortalEditFilterComponent(WebDriver driver) {
        super(driver);
    }

    public static ReportPortalEditFilterComponent using(WebDriver driver) {
        return new ReportPortalEditFilterComponent(driver);
    }

    public ReportPortalEditFilterComponent setName(String filterName) {
        $(nameField).shouldBe(visible).setValue(filterName);
        return this;
    }

    public ReportPortalEditFilterComponent setDescription(String filterDescription) {
        SelenideElement description = $(descriptionField).shouldBe(visible);
        Selenide.actions()
                .doubleClick(description)
                .keyDown(description, Keys.COMMAND)
                .keyDown(description, "a")
                .keyUp(description, Keys.COMMAND)
                .keyDown(description, Keys.BACK_SPACE)
                .sendKeys(description, filterDescription).build().perform();
        return this;
    }

    public void save() {
        $(saveButton).shouldBe(visible).click();
        WaiterUtils.pause();
    }

}
