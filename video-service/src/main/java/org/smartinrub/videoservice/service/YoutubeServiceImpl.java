package org.smartinrub.videoservice.service;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

//videos?id=7lCDEYXw3mM&key=AIzaSyCDC_o1Hhx1R5_0u2Fu2tPNY_I5G6OTBck&part=snippet,contentDetails,statistics,status
//https://www.youtube.com/watch?v=

//https://www.googleapis.com/youtube/v3/search?id=7lCDEYXw3mM&key=AIzaSyCDC_o1Hhx1R5_0u2Fu2tPNY_I5G6OTBck&part=id&q=surfing&maxResults=3&type=video

//https://developers.google.com/youtube/v3/docs/search/list

@Service
public class YoutubeServiceImpl implements YoutubeService {

    private static final String BASE_URL = "https://www.googleapis.com/youtube/v3/search?";
    private static final String YOUTUBE_API_KEY = System.getenv("YOUTUBE_API_KEY");
    private static final int MAX_RESULTS = 3;

    private final RestTemplate restTemplate;

    public YoutubeServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
    }

    @Override
    public List<String> getYoutubeLinksByMovieId(String videoId) {

        String movieTitle = "surf";

        String url = String.format("%s?id=%s&part=id&q=%s&maxResults=%d&type=video", BASE_URL, YOUTUBE_API_KEY, movieTitle, MAX_RESULTS);
        System.out.println(url);
        String videos = restTemplate.getForObject(url, String.class);
        return Arrays.asList(videos);
    }
}
