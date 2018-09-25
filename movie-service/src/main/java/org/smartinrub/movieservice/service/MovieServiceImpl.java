package org.smartinrub.movieservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.extern.slf4j.Slf4j;
import org.smartinrub.movieservice.model.Movie;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.security.auth.callback.CallbackHandler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class MovieServiceImpl implements MovieService {

    private static final String BASE_URL = "https://api.themoviedb.org/3/";

    private static final String THEMOVIEDB_API_KEY = System.getenv("THEMOVIEDB_API_KEY");

    private RestTemplate restTemplate = new RestTemplate();

    @Cacheable(cacheNames = "moviesByTitle")
    @Override
    public Optional<List<Movie>> getMoviesByTitle(String title) throws IOException {
        String url = BASE_URL + "search/movie?api_key=" + THEMOVIEDB_API_KEY + "&query=" + title;
        try {
           String string = restTemplate.getForEntity(url, String.class).getBody();

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
        System.out.println("get movie");
        String url = BASE_URL + "movie/" + id + "?api_key=" + THEMOVIEDB_API_KEY;
        try {
            return Optional.ofNullable(restTemplate.getForEntity(url, Movie.class).getBody());
        } catch (HttpClientErrorException e) {
            log.error("State {} when {}", e.getMessage(), url);
            return Optional.empty();
        }
    }
}
