package rp.testing.api.model.filter.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Type {
    @JsonProperty("Launch") LAUNCH,
    @JsonProperty("testitem") TEST_ITEM,
    @JsonProperty("log") LOG
}
