package rp.testing.api.utils;

import lombok.experimental.UtilityClass;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.RequestBuilder;
import rp.testing.utils.JsonUtils;

@UtilityClass
public class LoggingUtils {

    private static final String REQUEST_LOG = "Sending request:\n%s %s\n%s";
    private static final String RESPONSE_LOG = "Response received:\nStatus code - %d\n%s";

    public static String generateRequestLog(RequestBuilder request) {
        HttpEntity httpEntity = request.getEntity();
        String requestBody = httpEntity == null ? "" : JsonUtils.asPrettyJsonString(request.getEntity());
        return String.format(REQUEST_LOG, request.getMethod(), request.getUri().toString(), requestBody);
    }

    public static String generateResponseLog(String responseBody, int statusCode) {
        return String.format(RESPONSE_LOG, statusCode, JsonUtils.asPrettyJsonString(responseBody));
    }
}
