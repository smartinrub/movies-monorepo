package org.smartinrub.videoservice.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class YoutubeRepositoryImpl implements YoutubeRepository {

    private static final String BASE_YOUTUBE_API_URL = "https://www.googleapis.com/youtube/v3/search?";
    private static final String BASE_LINK_URL = "https://www.youtube.com/watch?v=";
    private static final String YOUTUBE_API_KEY = System.getenv("YOUTUBE_API_KEY");
    private static final int MAX_RESULTS = 3;

    private final RestTemplate restTemplate;

    public YoutubeRepositoryImpl(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
    }

    @Override
    public List<String> getYoutubeLinksByMovieTitle(String movieTitle) throws IOException {
        String url = BASE_YOUTUBE_API_URL + "key=" + YOUTUBE_API_KEY + "&part=id&q=" + movieTitle + "&maxResults="+ MAX_RESULTS + "&type=video";
        String string = restTemplate.getForObject(url, String.class);

        JsonNode arrayNode = new ObjectMapper().readTree(string).get("items");
        List<String> links = new ArrayList<>();
        if (arrayNode.isArray()) {
            for (final JsonNode node : arrayNode) {
                JsonNode nodeId = node.path("id").findPath("videoId");
                links.add(BASE_LINK_URL + nodeId.textValue());
            }
        }
        return links;
    }
}
