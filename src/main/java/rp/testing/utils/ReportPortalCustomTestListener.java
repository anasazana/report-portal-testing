package rp.testing.utils;

import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.engine.TestSource;
import org.junit.platform.engine.support.descriptor.MethodSource;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

import static org.apache.http.entity.ContentType.APPLICATION_JSON;

public class ReportPortalCustomTestListener implements TestExecutionListener {

    public void executionStarted(TestIdentifier testIdentifier) {
        String testCase = getTestCase(testIdentifier);
        if (testCase != null) {
            updateTestCaseStatus(testCase, TestCaseStatus.PROCESSING);
        }
    }

    public void executionFinished(TestIdentifier testIdentifier, TestExecutionResult testExecutionResult) {
        String testCase = getTestCase(testIdentifier);
        if (testCase != null) {
            updateTestCaseStatus(
                    testCase,
                    testExecutionResult.getStatus() == TestExecutionResult.Status.SUCCESSFUL
                            ? TestCaseStatus.PASSED
                            : TestCaseStatus.FAILED
            );
        }
    }

    private static String getTestCase(TestIdentifier testIdentifier) {
        TestSource testSource = testIdentifier.getSource().orElse(null);
        if (testSource instanceof MethodSource) {
            return Optional.ofNullable(((MethodSource) testSource).getJavaMethod())
                    .map(method -> method.getAnnotation(AlmLink.class))
                    .map(AlmLink::id)
                    .orElse(null);
        }
        return null;
    }

    private void updateTestCaseStatus(String testCase, TestCaseStatus testCaseStatus) {
        try (CloseableHttpClient httpClient = HttpClients.custom().build()) {
            RequestBuilder request = RequestBuilder
                    .post(String.format("%s/rest/api/2/issue/%s/transitions", TestConfiguration.jiraUrl(), testCase))
                    .setEntity(new StringEntity("{\"transition\":{\"id\":\"" + testCaseStatus.getId() + "\"}}"))
                    .setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON.getMimeType())
                    .setHeader(HttpHeaders.AUTHORIZATION, getJiraBasicAuthenticationHeader());
            try (CloseableHttpResponse closeableHttpResponse = httpClient.execute(request.build())) {
                closeableHttpResponse.getEntity();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getJiraBasicAuthenticationHeader() {
        String valueToEncode = TestConfiguration.jiraUsername() + ":" + TestConfiguration.jiraPassword();
        return "Basic " + Base64.getEncoder().encodeToString(valueToEncode.getBytes());
    }
}
