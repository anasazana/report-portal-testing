package rp.testing.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OwnedEntityResource {

    @JsonProperty
    private String id;
    @JsonProperty
    private String name;
    @JsonProperty
    private String owner;
    @JsonProperty
    private String description;

}
