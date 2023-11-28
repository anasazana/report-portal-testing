package rp.testing.api.client.newclient;

import io.restassured.response.ValidatableResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import rp.testing.api.model.filter.UpdateUserFilterRQ;
import rp.testing.api.model.filter.enums.RequestParameter;
import rp.testing.utils.JsonUtils;

import java.util.HashMap;
import java.util.Map;

import static rp.testing.utils.TestConfiguration.projectName;

@Slf4j
public class ReportPortalFiltersNewClient extends ReportPortalNewServiceClient {

    private static final String FILTER = "/filter/";

    public ReportPortalFiltersNewClient() {
        super(projectName() + FILTER);
    }

    public ValidatableResponse getFilters() {
        return getFiltersWithParameters(new HashMap<>());
    }

    public ValidatableResponse getFiltersWithParameters(Map<RequestParameter, String> requestParameters) {
        return get(getParametersString(requestParameters));
    }

    public ValidatableResponse getFilterById(String filterId) {
        return get(filterId);
    }

    public ValidatableResponse createFilter(UpdateUserFilterRQ newFilter) {
        return post(StringUtils.EMPTY, JsonUtils.asPrettyJsonString(newFilter));
    }

    public ValidatableResponse updateFilterById(String filterId,
                                                UpdateUserFilterRQ updatedFilter) {
        return put(filterId, JsonUtils.asPrettyJsonString(updatedFilter));
    }

    public ValidatableResponse deleteFilterById(String filterId) {
        return delete(filterId);
    }

}
