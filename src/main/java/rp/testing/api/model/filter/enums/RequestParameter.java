package rp.testing.api.model.filter.enums;

import lombok.Getter;

@Getter
public enum RequestParameter {
    FILTERS_EQ_ID("filter.eq.id"),
    FILTERS_EQ_NAME("filter.eq.name"),
    FILTERS_EQ_OWNER("filter.eq.owner"),
    FILTERS_EQ_PROJECT_ID("filter.eq.projectId"),
    PAGE_PAGE("page.page"),
    PAGE_SIZE("page.size"),
    PAGE_SORT("page.sort");

    private final String parameterName;

    RequestParameter(String parameter) {
        parameterName = parameter;
    }

}
