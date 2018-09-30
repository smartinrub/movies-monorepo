package org.smartinrub.orchestratorservice.client;

import lombok.extern.slf4j.Slf4j;
import org.smartinrub.orchestratorservice.model.Movie;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class MovieClientFallback implements MovieClient {
    @Override
    public List<Movie> getAll(String title) {
        return Collections.emptyList();
    }

    @Override
    public Movie getById(long movieId) {
        log.error("Movie Service unavailable!");
        return new Movie("","","", "", Collections.emptyList(), Collections.emptyList());
    }
}
