package org.smartinrub.orchestratorservice.client;

import lombok.extern.slf4j.Slf4j;
import org.smartinrub.orchestratorservice.model.Review;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class ReviewClientFallback implements ReviewClient {
    @Override
    public void save(Review review) {
        log.error("Reviews Servie unavailable!");
    }

    @Override
    public List<Review> getAllByMovieId(long movieId) {
        log.error("Reviews Service unavailable!");
        return Collections.emptyList();
    }
}
