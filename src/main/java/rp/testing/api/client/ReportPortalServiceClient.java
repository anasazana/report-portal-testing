package rp.testing.api.client;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import rp.testing.api.model.filter.enums.RequestParameter;
import rp.testing.api.validator.ReportPortalResponseValidator;

import java.io.IOException;
import java.util.Map;
import java.util.function.Supplier;

import static org.apache.http.entity.ContentType.APPLICATION_JSON;
import static org.apache.http.entity.ContentType.WILDCARD;
import static rp.testing.api.utils.LoggingUtils.generateRequestLog;
import static rp.testing.utils.TestConfiguration.apiBaseUrl;
import static rp.testing.utils.TestConfiguration.token;

@Slf4j
public abstract class ReportPortalServiceClient {

    protected final CloseableHttpClient httpClient;
    protected final String baseUrl;
    protected final String token;

    protected ReportPortalServiceClient(String url) {
//        HttpHost proxy = new HttpHost("localhost", 8866, "http");
//        DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
        httpClient = HttpClients.custom()
//                .setRoutePlanner(routePlanner)
                .build();
        baseUrl = apiBaseUrl() + url;
        token = "Bearer " + token();
    }

    protected CloseableHttpResponse get(String url) {
        try {
            RequestBuilder request = RequestBuilder.get(baseUrl + url)
                    .setHeader(HttpHeaders.AUTHORIZATION, token);
            log.info(generateRequestLog(request));
            return httpClient.execute(request.build());
        } catch (IOException e) {
            throw new RuntimeException("Exception occurred while sending GET request:\n" + e.getMessage());
        }
    }

    protected CloseableHttpResponse post(String url, StringEntity requestBody) {
        try {
            RequestBuilder request = RequestBuilder.post(baseUrl + url)
                    .setHeader(HttpHeaders.AUTHORIZATION, token)
                    .setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON.getMimeType())
                    .setHeader(HttpHeaders.ACCEPT, WILDCARD.getMimeType())
                    .setEntity(requestBody);
            log.info(generateRequestLog(request));
            return httpClient.execute(request.build());
        } catch (IOException e) {
            throw new RuntimeException("Exception occurred while sending POST request:\n" + e.getMessage());
        }
    }

    protected CloseableHttpResponse put(String url, StringEntity requestBody) {
        try {
            RequestBuilder request = RequestBuilder.put(baseUrl + url)
                    .setHeader(HttpHeaders.AUTHORIZATION, token)
                    .setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON.getMimeType())
                    .setHeader(HttpHeaders.ACCEPT, WILDCARD.getMimeType())
                    .setEntity(requestBody);
            log.info(generateRequestLog(request));
            return httpClient.execute(request.build());
        } catch (IOException e) {
            throw new RuntimeException("Exception occurred while sending PUT request:\n" + e.getMessage());
        }
    }

    protected CloseableHttpResponse delete(String url) {
        try {
            RequestBuilder request = RequestBuilder.delete(baseUrl + url)
                    .setHeader(HttpHeaders.AUTHORIZATION, token)
                    .setHeader(HttpHeaders.ACCEPT, WILDCARD.getMimeType());
            log.info(generateRequestLog(request));
            return httpClient.execute(request.build());
        } catch (IOException e) {
            throw new RuntimeException("Exception occurred while sending DELETE request:\n" + e.getMessage());
        }
    }

    public void closeHttpClient() throws IOException {
        httpClient.close();
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

    protected ReportPortalResponseValidator getResponseValidator(Supplier<CloseableHttpResponse> supplier)
            throws IOException {
        try (CloseableHttpResponse closeableHttpResponse = supplier.get()) {
            return new ReportPortalResponseValidator(
                    EntityUtils.toString(closeableHttpResponse.getEntity()),
                    closeableHttpResponse.getStatusLine().getStatusCode()
            );
        }
    }

}
