package rp.testing.junit.tests.api.testsuites;

import org.junit.jupiter.api.DisplayName;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@DisplayName("RP Filters API tests")
@IncludeEngines("junit-jupiter")
@SelectPackages({
//        "rp.testing.junit.tests.api.apachehttpclient",
        "rp.testing.junit.tests.api.newclient"
})
public class ReportPortalFiltersApiTest {
}
