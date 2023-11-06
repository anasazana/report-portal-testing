package rp.testing.api.model.filter;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Set;

public class UserFilterResource {

    @JsonProperty
    private Long filterId;
    @JsonProperty
    private String name;
    @JsonProperty
    private Set<UserFilterCondition> conditions;
    @JsonProperty
    private List<Order> orders;
    @JsonProperty
    private String objectType;
    @JsonProperty
    private String owner;
}
