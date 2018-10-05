package org.smartinrub.orchestratorservice.client;

import org.smartinrub.orchestratorservice.model.Review;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value = "reviews-service", fallback = ReviewClientFallback.class)
public interface ReviewClient {

    @PostMapping(value = "/", consumes = "application/json")
    void save(Review review);

    @GetMapping("{movieId}")
    List<Review> getAllByMovieId(@PathVariable("movieId") long movieId);
}
