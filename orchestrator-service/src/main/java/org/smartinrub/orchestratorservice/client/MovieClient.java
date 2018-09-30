package org.smartinrub.orchestratorservice.client;

import org.smartinrub.orchestratorservice.model.Movie;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "movie-service", fallback = MovieClientFallback.class)
public interface MovieClient {

    @GetMapping(value = "/{title}")
    List<Movie> getAll(@PathVariable("title") String title);

    @GetMapping(value = "/movie/{movieId}")
    Movie getById(@PathVariable("movieId") final long movieId);
}
