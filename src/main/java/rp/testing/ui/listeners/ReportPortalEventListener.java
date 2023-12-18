package rp.testing.ui.listeners;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;

import java.util.Arrays;

@Slf4j
public class ReportPortalEventListener implements WebDriverListener {

    @Override
    public void beforeFindElement(WebDriver driver, By locator) {
        log.debug("Find element '{}'", locator.toString());
    }

    @Override
    public void beforeFindElements(WebDriver driver, By locator) {
        log.debug("Find elements '{}'", locator.toString());
    }

    @Override
    public void beforeExecuteScript(WebDriver driver, String script, Object[] args) {
        log.info("Execute script: '{}' with args: {}", script, Arrays.toString(args));
    }

    @Override
    public void beforeClick(WebElement element) {
        log.info("Click on element '{}'", element.getText());
    }

}
