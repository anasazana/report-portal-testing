package rp.testing.ui.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.events.EventFiringDecorator;
import rp.testing.ui.listeners.ReportPortalEventListener;
import rp.testing.utils.TestConfiguration;

import java.util.List;
import java.util.logging.Level;

import static rp.testing.utils.WaiterUtils.TIMEOUT_30_S;

public enum BrowserFactory {

    CHROME {
        @Override
        public WebDriver createDriver() {
            WebDriverManager.getInstance(DriverManagerType.CHROME).create();
            WebDriver baseDriver = new ChromeDriver(getOptions());
            EventFiringDecorator<WebDriver> eventsDriver = new EventFiringDecorator<>(new ReportPortalEventListener());
            return eventsDriver.decorate(baseDriver);
        }

        @Override
        public ChromeOptions getOptions() {
            LoggingPreferences logging = new LoggingPreferences();
            logging.enable(LogType.DRIVER, Level.ALL);
            ChromeOptions options = new ChromeOptions();
            if (TestConfiguration.headless()) {
                options.addArguments("--headless=new");
            }
            options.addArguments(COMMON_OPTIONS)
                    .setPageLoadTimeout(TIMEOUT_30_S)
                    .setCapability(ChromeOptions.LOGGING_PREFS, logging);
            return options;
        }
    },
    FIREFOX {
        @Override
        public WebDriver createDriver() {
            WebDriverManager.getInstance(DriverManagerType.FIREFOX).setup();
            return new FirefoxDriver(getOptions());
        }

        @Override
        public FirefoxOptions getOptions() {
            return new FirefoxOptions().addArguments(COMMON_OPTIONS);
        }
    };

    public static final List<String> COMMON_OPTIONS = List.of(
            "--start-maximized",
            "--disable-infobars",
            "--disable-notifications"
    );
    public abstract WebDriver createDriver();
    public abstract MutableCapabilities getOptions();

}
