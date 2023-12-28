package rp.testing.junit.tests.ui.selenide.suites;

import org.junit.jupiter.api.DisplayName;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@DisplayName("RP Filters UI tests")
@IncludeEngines("junit-jupiter")
@SelectPackages({
        "rp.testing.junit.tests.ui.selenide"
})
public class ReportPortalFiltersSelenideUITest {

}
