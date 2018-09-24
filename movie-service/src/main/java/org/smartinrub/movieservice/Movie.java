package org.smartinrub.movieservice;

import lombok.Data;

@Data
public class Movie {

    private final String title;
    private final String cover;
    private final String release;
    private final String genre;
    private final String producer;
}
