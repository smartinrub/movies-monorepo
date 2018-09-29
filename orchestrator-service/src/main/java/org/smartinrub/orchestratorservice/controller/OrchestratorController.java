package org.smartinrub.orchestratorservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.smartinrub.orchestratorservice.client.MovieClient;
import org.smartinrub.orchestratorservice.client.ReviewClient;
import org.smartinrub.orchestratorservice.client.VideoClient;
import org.smartinrub.orchestratorservice.model.Movie;
import org.smartinrub.orchestratorservice.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/movies")
public class OrchestratorController {

    private final ReviewClient reviewClient;

    private final MovieClient movieClient;

    private final VideoClient videoClient;

    @Autowired
    public OrchestratorController(ReviewClient reviewClient, MovieClient movieClient, VideoClient videoClient) {
        this.reviewClient = reviewClient;
        this.movieClient = movieClient;
        this.videoClient = videoClient;
    }

    @PostMapping("/movie/review")
    @ResponseStatus(HttpStatus.CREATED)
    public void addReview(@Valid @RequestBody Review review) {
        reviewClient.save(review);
    }

    @GetMapping("/{title}")
    public List<Movie> getAllMoviesByTitle(@PathVariable("title") String title) {
        return movieClient.getAll(title);

    }

    @GetMapping("/movie/{movieId}")
    public Movie getMovie(@PathVariable("movieId") final long movieId) {
        Movie movie = movieClient.getById(movieId);
        List<String> links = videoClient.getLinks(movieId, movie.getTitle());
        List<Review> reviews = reviewClient.getAllByMovieId(movieId);
        movie.setLinks(links);
        movie.setReviews(reviews);
        return movie;
    }
}
