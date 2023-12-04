package rp.testing.ui.driver;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import rp.testing.utils.TestConfiguration;

import java.net.MalformedURLException;
import java.net.URL;

@Slf4j
public class WebDriverFactory {

    public static WebDriver createInstance(String browserName) {
        WebDriver webdriver = null;

        switch (TestConfiguration.runMode()) {
            case LOCAL:
                webdriver = BrowserFactory.valueOf(browserName).createDriver();
                break;
            case REMOTE:
                webdriver = createRemoteInstance(BrowserFactory.valueOf(browserName).getOptions());
                break;
            default:
                log.error("Wrong run mode! Available: LOCAL, REMOTE");
        }

        return webdriver;
    }

    private static RemoteWebDriver createRemoteInstance(MutableCapabilities capability) {
        RemoteWebDriver remoteWebDriver = null;
        try {
            remoteWebDriver = new RemoteWebDriver(new URL(TestConfiguration.gridUrl()), capability);
        } catch (MalformedURLException e) {
            log.error("Grid URL is invalid or Grid is not available");
            log.error(String.format("Browser: %s", capability.getBrowserName()), e);
        } catch (IllegalArgumentException e) {
            log.error(String.format("Browser %s is not valid or recognized", capability.getBrowserName()), e);
        }

        return remoteWebDriver;
    }

}
