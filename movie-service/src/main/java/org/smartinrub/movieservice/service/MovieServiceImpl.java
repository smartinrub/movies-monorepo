package org.smartinrub.movieservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.smartinrub.movieservice.model.Movie;
import org.smartinrub.movieservice.util.TheMovieDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class MovieServiceImpl implements MovieService {

    private static final String BASE_URL = "https://api.themoviedb.org/3/";

    private final TheMovieDb theMovieDbProperties;

    private final RestTemplate restTemplate;

    @Autowired
    public MovieServiceImpl(RestTemplateBuilder restTemplateBuilder, TheMovieDb theMovieDbProperties) {
        restTemplate = restTemplateBuilder.build();
        this.theMovieDbProperties = theMovieDbProperties;
    }

    @Cacheable(cacheNames = "moviesByTitle")
    @Override
    public Optional<List<Movie>> getMoviesByTitle(String title) throws IOException {
        String url = BASE_URL + "search/movie?api_key=" + theMovieDbProperties.getKey() + "&query=" + title;
        try {
           String string = restTemplate.getForObject(url, String.class);

           JsonNode arrayNode = new ObjectMapper().readTree(string).get("results");
           List<Movie> movies = new ArrayList<>();
            if (arrayNode.isArray()) {
                for (final JsonNode node : arrayNode) {
                    Movie movie = new ObjectMapper().treeToValue(node, Movie.class);
                    movies.add(movie);
                }
            }
            return Optional.of(movies);
        } catch (HttpClientErrorException e) {
            log.error("State {} when {}", e.getMessage(), url);
            return Optional.empty();
        }
    }

    @Cacheable(cacheNames = "movieById")
    @Override
    public Optional<Movie> getMovieById(Long id) {
        String url = BASE_URL + "movie/" + id + "?api_key=" + theMovieDbProperties.getKey();
        try {
            return Optional.ofNullable(restTemplate.getForObject(url, Movie.class));
        } catch (HttpClientErrorException e) {
            log.error("State {} when {}", e.getMessage(), url);
            return Optional.empty();
        }
    }
}
