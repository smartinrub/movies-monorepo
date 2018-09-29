package org.smartinrub.reviewsservice.controller;

import org.smartinrub.reviewsservice.model.Review;
import org.smartinrub.reviewsservice.repository.ReviewsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ReviewsController {

    private final ReviewsRepository reviewsRepository;

    public ReviewsController(ReviewsRepository reviewsRepository) {
        this.reviewsRepository = reviewsRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addReview(@Valid @RequestBody Review review){
        reviewsRepository.save(review);
    }

    @GetMapping("{movie_id}")
    public ResponseEntity<List<Review>> getAllReviewsByMovieId(@PathVariable("movie_id") final String movieId) {
        return ResponseEntity.ok(reviewsRepository.findAllByMovieId(movieId));
    }
}
