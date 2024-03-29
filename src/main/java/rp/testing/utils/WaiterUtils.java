package rp.testing.utils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@UtilityClass
public class WaiterUtils {

    public static final Duration TIMEOUT_30_S = Duration.ofSeconds(30);
    public static final Duration TIMEOUT_60_S = Duration.ofSeconds(60);
    private static final long PAUSE_MS = 700;

    public static void pause() {
        pause(PAUSE_MS);
    }

    @SneakyThrows
    public static void pause(long millis) {
        Thread.sleep(millis);
    }

    public static void waitForPageToBeLoadedJs(WebDriver driver, Duration timeoutInSeconds) {
        new WebDriverWait(driver, timeoutInSeconds).until(
                wd -> ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete")
        );
    }

}
