package org.smartinrub.movieservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Value;
import org.springframework.cache.annotation.Cacheable;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Movie implements Serializable {

    private final String id;

    private final String title;

    @JsonProperty("poster_path")
    private final String cover;

    @JsonProperty("release_date")
    private final String releaseDate;

    private final List<Map<String, String>> genres;

    @JsonProperty("production_companies")
    private final List<Producer> producers;
}
