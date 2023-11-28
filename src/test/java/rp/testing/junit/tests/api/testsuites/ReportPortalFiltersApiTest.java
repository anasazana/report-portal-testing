package rp.testing.junit.tests.api.testsuites;

import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeEngines("junit-jupiter")
@SelectPackages({"rp.testing.junit.tests.api"})
public class ReportPortalFiltersApiTest {
}
