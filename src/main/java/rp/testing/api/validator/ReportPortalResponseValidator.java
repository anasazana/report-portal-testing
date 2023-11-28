package rp.testing.api.validator;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import rp.testing.api.utils.LoggingUtils;
import rp.testing.utils.JsonUtils;

@Slf4j
public class ReportPortalResponseValidator {

    private String responseBody;
    private int statusCode;

    public ReportPortalResponseValidator(String responseBody, int statusCode) {
        this.responseBody = responseBody;
        this.statusCode = statusCode;
        log.info(LoggingUtils.generateResponseLog(responseBody, statusCode));
    }

    public ReportPortalResponseValidator validateStatusCode(int expectedStatus) {
        Assertions.assertThat(statusCode).isEqualTo(expectedStatus);
        return this;
    }

    public <T> ReportPortalResponseValidator validateResponseBodyEquals(T expectedBodyObject) {
        Assertions.assertThat(JsonUtils.fromJsonToObject(responseBody, expectedBodyObject.getClass()))
                .usingRecursiveComparison().isEqualTo(expectedBodyObject);
        return this;
    }

    public <T> T getBodyAsObject(Class<T> clazz) {
        return JsonUtils.fromJsonToObject(responseBody, clazz);
    }

}
