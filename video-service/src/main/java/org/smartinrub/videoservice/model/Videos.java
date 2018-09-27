package org.smartinrub.videoservice.model;

import lombok.Data;

import java.util.List;

@Data
public class Videos {

    private final String movieId;
    private final List<String> links;

}
