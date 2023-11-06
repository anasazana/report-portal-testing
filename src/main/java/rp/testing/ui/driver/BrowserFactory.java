package rp.testing.ui.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import rp.testing.utils.TestConfiguration;

import java.util.List;

public enum BrowserFactory {

    CHROME {
        @Override
        public WebDriver createDriver() {
            WebDriverManager.getInstance(DriverManagerType.CHROME).create();
            return new ChromeDriver(getOptions());
        }

        @Override
        public ChromeOptions getOptions() {
            return new ChromeOptions().addArguments(COMMON_OPTIONS).setHeadless(TestConfiguration.headless());
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
            return new FirefoxOptions().addArguments(COMMON_OPTIONS).setHeadless(TestConfiguration.headless());
        }
    },
    OPERA {
        @Override
        public WebDriver createDriver() {
            WebDriverManager.getInstance(DriverManagerType.OPERA).create();
            return new OperaDriver(getOptions());
        }

        @Override
        public OperaOptions getOptions() {
            return new OperaOptions().addArguments(COMMON_OPTIONS).addArguments("-headless");
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
