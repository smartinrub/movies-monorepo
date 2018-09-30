package org.smartinrub.orchestratorservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "video-service", fallback = VideoClientFallback.class)
public interface VideoClient {

    @GetMapping("{movieId}")
    List<String> getLinks(@PathVariable("movieId") final long movieId, @RequestParam("title") final String movieTitle);
}
