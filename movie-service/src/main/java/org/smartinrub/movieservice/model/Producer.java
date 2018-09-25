package org.smartinrub.movieservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;

@Value
class Producer implements Serializable {

    private final String id;

    @JsonProperty("logo_path")
    private final String logo;

    private final String name;

    @JsonProperty("origin_country")
    private final String country;
}
