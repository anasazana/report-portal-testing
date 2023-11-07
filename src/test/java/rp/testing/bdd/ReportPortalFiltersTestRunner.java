package rp.testing.bdd;

import io.cucumber.junit.platform.engine.Cucumber;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.SelectClasspathResource;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

@Cucumber
@SelectClasspathResource(value = "rp/testing/bdd")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "rp.testing.bdd")
public class ReportPortalFiltersTestRunner {

}
