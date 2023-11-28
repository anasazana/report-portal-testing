package rp.testing.api.client;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import rp.testing.api.model.filter.UpdateUserFilterRQ;
import rp.testing.api.model.filter.enums.RequestParameter;
import rp.testing.api.validator.ReportPortalResponseValidator;
import rp.testing.utils.JsonUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static rp.testing.utils.TestConfiguration.projectName;

@Slf4j
public class ReportPortalFiltersClient extends ReportPortalServiceClient {

    private static final String FILTER = "/filter/";

    public ReportPortalFiltersClient() {
        super(projectName() + FILTER);
    }

    public ReportPortalResponseValidator getFilters() throws IOException {
        return getFiltersWithParameters(new HashMap<>());
    }

    public ReportPortalResponseValidator getFiltersWithParameters(Map<RequestParameter, String> requestParameters)
            throws IOException {
        return getResponseValidator(() -> get(getParametersString(requestParameters)));
    }

    public ReportPortalResponseValidator getFilterById(String filterId) throws IOException {
        return getResponseValidator(() -> get(filterId));
    }

    public ReportPortalResponseValidator createFilter(UpdateUserFilterRQ newFilter) throws IOException {
        return getResponseValidator(() -> post(StringUtils.EMPTY, JsonUtils.asStringEntity(newFilter)));
    }

    public ReportPortalResponseValidator updateFilterById(String filterId, UpdateUserFilterRQ updatedFilter)
            throws IOException {
        return getResponseValidator(() -> put(filterId, JsonUtils.asStringEntity(updatedFilter)));
    }

    public ReportPortalResponseValidator deleteFilterById(String filterId) throws IOException {
        return getResponseValidator(() -> delete(filterId));
    }

}
