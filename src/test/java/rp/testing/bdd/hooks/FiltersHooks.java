package rp.testing.bdd.hooks;

import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rp.testing.ui.pageobjects.ReportPortalLoginPage;
import rp.testing.utils.TestConfiguration;

import static rp.testing.bdd.ReportPortalBddConfiguration.getDriver;

public class FiltersHooks {

    private static final Logger logger = LoggerFactory.getLogger(FiltersHooks.class);

    @Before
    public void navigateToLaunchesPage() {
        ReportPortalLoginPage loginPage = ReportPortalLoginPage.using(getDriver()).launch();
        if (!loginPage.isLoggedIn()) {
            logger.info("Login as {} user", TestConfiguration.username());
            loginPage.login(TestConfiguration.username(), TestConfiguration.password());
        }
        logger.info("Navigate to RP Launches web page");
        getDriver().navigate().to(TestConfiguration.launchesPageUrl());
    }

    @AfterAll
    public static void closeBrowser() {
        logger.info("Close web browser");
        getDriver().quit();
    }

}
