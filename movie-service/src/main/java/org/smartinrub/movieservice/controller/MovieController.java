package org.smartinrub.movieservice.controller;

import org.smartinrub.movieservice.model.Movie;
import org.smartinrub.movieservice.service.MovieServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
public class MovieController {

    private final MovieServiceImpl movieService;

    public MovieController(MovieServiceImpl movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/movies/{title}")
    public ResponseEntity<List<Movie>> getMoviesByTitle(@NotNull @PathVariable("title") String title) throws IOException {
        Optional<List<Movie>> movies = movieService.getMoviesByTitle(title);
        if (!movies.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(movies.get());
    }

    @GetMapping("/movie/{id}")
    public ResponseEntity<Movie> getMovieById(@NotNull @PathVariable("id") Long id) {
        Optional<Movie> movie = movieService.getMovieById(id);
        if (!movie.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(movie.get());
    }
}
