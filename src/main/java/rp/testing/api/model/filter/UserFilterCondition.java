package rp.testing.api.model.filter;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserFilterCondition {

    @JsonProperty
    private String filteringField;
    @JsonProperty
    private String condition;
    @JsonProperty
    private String value;

}
