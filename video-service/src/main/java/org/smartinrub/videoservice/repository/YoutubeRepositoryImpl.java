package org.smartinrub.videoservice.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.smartinrub.videoservice.util.Youtube;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class YoutubeRepositoryImpl implements YoutubeRepository {

    private static final String BASE_YOUTUBE_API_URL = "https://www.googleapis.com/youtube/v3/search?";
    private static final String BASE_VIDEO_LINK_URL = "https://www.youtube.com/watch?v=";

    private final RestTemplate restTemplate;

    private final Youtube youtubeProperties;

    public YoutubeRepositoryImpl(RestTemplateBuilder restTemplateBuilder, Youtube youtubeProperties) {
        restTemplate = restTemplateBuilder.build();
        this.youtubeProperties = youtubeProperties;
    }

    @Override
    public List<String> getYoutubeLinksByMovieTitle(String movieTitle) throws IOException {
        String url = BASE_YOUTUBE_API_URL +
                "key=" + youtubeProperties.getKey() +
                "&part=id&q=" + movieTitle +
                "&maxResults="+ youtubeProperties.getMaxResults() +
                "&type=video";
        try {
            String string = restTemplate.getForObject(url, String.class);
            JsonNode arrayNode = new ObjectMapper().readTree(string).get("items");
            List<String> links = new ArrayList<>();
            if (arrayNode.isArray()) {
                for (final JsonNode node : arrayNode) {
                    JsonNode nodeId = node.path("id").findPath("videoId");
                    links.add(BASE_VIDEO_LINK_URL + nodeId.textValue());
                }
            }
            return links;

        } catch (HttpClientErrorException e) {
            return Collections.emptyList();
        }
    }
}
