package org.smartinrub.movieservice.service;

import org.smartinrub.movieservice.model.Movie;

import java.io.IOException;
import java.util.Optional;

public interface MovieService {

    Optional<String> getMoviesByTitle(String title) throws IOException;

    Optional<Movie> getMovieById(Long id);
}
