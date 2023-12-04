package rp.testing.api.client.newclient;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import rp.testing.api.model.filter.enums.RequestParameter;
import rp.testing.api.validator.newvalidator.ReportPortalNewResponseValidator;

import java.util.Map;

import static org.apache.http.entity.ContentType.APPLICATION_JSON;
import static org.apache.http.entity.ContentType.WILDCARD;
import static rp.testing.utils.TestConfiguration.apiBaseUrl;
import static rp.testing.utils.TestConfiguration.token;

@Slf4j
public abstract class ReportPortalNewServiceClient {

    protected final String token;
    private final RequestSpecBuilder baseRequestSpec;

    protected ReportPortalNewServiceClient(String url) {
        RestAssured.baseURI = apiBaseUrl() + url;
        RestAssured.proxy("localhost", 8866, "http");
        token = "Bearer " + token();
        baseRequestSpec = new RequestSpecBuilder().addHeader(HttpHeaders.AUTHORIZATION, token);
    }

    protected ReportPortalNewResponseValidator get(String url) {
        return new ReportPortalNewResponseValidator(
                RestAssured.given(baseRequestSpec.build())
                        .log().all()
                        .when().get(url)
                        .then().log().all()
        );
    }

    protected ReportPortalNewResponseValidator post(String url, String requestBody) {
        return new ReportPortalNewResponseValidator(
                RestAssured.given(baseRequestSpec.build())
                        .contentType(APPLICATION_JSON.getMimeType())
                        .accept(WILDCARD.getMimeType())
                        .body(requestBody)
                        .log().all()
                        .when().post(url).then().log().all()
        );
    }

    protected ReportPortalNewResponseValidator put(String url, String requestBody) {
        return new ReportPortalNewResponseValidator(
                RestAssured.given(baseRequestSpec.build())
                        .contentType(APPLICATION_JSON.getMimeType())
                        .accept(WILDCARD.getMimeType())
                        .body(requestBody)
                        .log().all()
                        .when().put(url).then().log().all()
        );
    }

    protected ReportPortalNewResponseValidator delete(String url) {
        return new ReportPortalNewResponseValidator(
                RestAssured.given(baseRequestSpec.build())
                        .accept(WILDCARD.getMimeType())
                        .log().all()
                        .when().delete(url).then().log().all()
        );
    }

    protected String getParametersString(Map<RequestParameter, String> requestParameters) {
        StringBuilder parameters = new StringBuilder();
        if (!requestParameters.isEmpty()) {
            parameters.append("?");
            int paramNum = requestParameters.size();
            for (Map.Entry<RequestParameter, String> param : requestParameters.entrySet()) {
                parameters.append(param.getKey().getParameterName()).append("=").append(param.getValue());
                if (--paramNum > 0) {
                    parameters.append("&");
                }
            }
        }
        return parameters.toString();
    }

}
