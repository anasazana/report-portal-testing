package rp.testing.ui.driver;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rp.testing.utils.TestConfiguration;

import java.net.MalformedURLException;
import java.net.URL;

public class WebDriverFactory {

    final static Logger logger = LoggerFactory.getLogger(WebDriverFactory.class);

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
                logger.error("Wrong run mode! Available: LOCAL, REMOTE");
        }

        return webdriver;
    }

    private static RemoteWebDriver createRemoteInstance(MutableCapabilities capability) {
        RemoteWebDriver remoteWebDriver = null;
        try {
            remoteWebDriver = new RemoteWebDriver(new URL(TestConfiguration.gridUrl()), capability);
        } catch (MalformedURLException e) {
            logger.error("Grid URL is invalid or Grid is not available");
            logger.error(String.format("Browser: %s", capability.getBrowserName()), e);
        } catch (IllegalArgumentException e) {
            logger.error(String.format("Browser %s is not valid or recognized", capability.getBrowserName()), e);
        }

        return remoteWebDriver;
    }

}
