package org.smartinrub.orchestratorservice.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class VideoClientFallback implements VideoClient {
    @Override
    public List<String> getLinks(long movieId, String movieTitle) {
        log.error("Video Service Unavailable!");
        return Collections.emptyList();
    }
}
