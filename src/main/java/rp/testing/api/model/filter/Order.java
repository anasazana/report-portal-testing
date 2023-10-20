package rp.testing.api.model.filter;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Order {

    @JsonProperty
    private String sortingColumn;
    @JsonProperty
    private boolean isAsc;
}
