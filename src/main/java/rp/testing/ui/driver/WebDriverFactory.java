package rp.testing.ui.driver;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import rp.testing.utils.TestConfiguration;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@UtilityClass
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
            case SAUCELABS:
                webdriver = createSauceLabsInstance(BrowserFactory.valueOf(browserName).getOptions());
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

    private static RemoteWebDriver createSauceLabsInstance(MutableCapabilities capability) {
        RemoteWebDriver remoteWebDriver = null;
        try {
            Map<String, Object> sauceOptions = new HashMap<>();
            sauceOptions.put("username", TestConfiguration.saucelabUsername());
            sauceOptions.put("accessKey", TestConfiguration.saucelabsAccessKey());
            sauceOptions.put("build", TestConfiguration.saucelabsBuild());
            sauceOptions.put("name", TestConfiguration.saucelabsTestName());
            sauceOptions.put("tunnel-name", TestConfiguration.saucelabsTunnelName());
            capability.setCapability("sauce:options", sauceOptions);
            remoteWebDriver = new RemoteWebDriver(new URL(TestConfiguration.saucelabsUrl()), capability);
        } catch (MalformedURLException e) {
            log.error("Grid URL is invalid or Grid is not available");
            log.error(String.format("Browser: %s", capability.getBrowserName()), e);
        } catch (IllegalArgumentException e) {
            log.error(String.format("Browser %s is not valid or recognized", capability.getBrowserName()), e);
        }

        return remoteWebDriver;
    }

}
