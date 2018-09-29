package org.smartinrub.orchestratorservice.client;

import org.smartinrub.orchestratorservice.model.Review;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("reviews-service")
public interface ReviewClient {

    @PostMapping(value = "/", consumes = "application/json")
    void save(Review review);
}
