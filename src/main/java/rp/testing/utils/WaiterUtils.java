package rp.testing.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaiterUtils {

    public static final Duration TIMEOUT_30_S = Duration.ofSeconds(30);
    public static final Duration TIMEOUT_60_S = Duration.ofSeconds(60);
    private static final long PAUSE_MS = 700;

    public static void pause() {
        pause(PAUSE_MS);
    }

    public static void pause(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void waitForPageToBeLoadedJs(WebDriver driver, Duration timeoutInSeconds) {
        new WebDriverWait(driver, timeoutInSeconds).until(
                wd -> ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete")
        );
    }

}
