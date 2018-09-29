package org.smartinrub.reviewsservice.repository;

import org.smartinrub.reviewsservice.model.Review;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ReviewsRepository extends MongoRepository<Review, String> {

    @Query("{'movieid': '?0'}")
    List<Review> findAllByMovieId(String movieId);
}
