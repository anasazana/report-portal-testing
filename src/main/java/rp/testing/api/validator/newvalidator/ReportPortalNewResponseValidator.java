package rp.testing.api.validator.newvalidator;

import io.restassured.response.ValidatableResponse;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import rp.testing.utils.JsonUtils;

@Slf4j
public class ReportPortalNewResponseValidator {

    private final ValidatableResponse response;
    private String jsonBody;

    public ReportPortalNewResponseValidator(ValidatableResponse response) {
        this.response = response;
    }

    public ReportPortalNewResponseValidator validateStatusCode(int expectedStatus) {
        response.assertThat().statusCode(expectedStatus);
        return this;
    }

    public <T> ReportPortalNewResponseValidator validateResponseBodyEquals(T expectedBodyObject) {
        jsonBody = response.extract().asPrettyString();
        Assertions.assertThat(JsonUtils.fromJsonToObject(jsonBody, expectedBodyObject.getClass()))
                .usingRecursiveComparison().isEqualTo(expectedBodyObject);
        return this;
    }

    public <T> T getBodyAsObject(Class<T> clazz) {
        if (jsonBody == null) {
            jsonBody = response.extract().asPrettyString();
        }
        return JsonUtils.fromJsonToObject(jsonBody, clazz);
    }

}
