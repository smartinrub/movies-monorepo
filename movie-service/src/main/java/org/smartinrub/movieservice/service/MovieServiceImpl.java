package org.smartinrub.movieservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.smartinrub.movieservice.model.Movie;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {

    private static final String BASE_URL = "https://api.themoviedb.org/3/";

    private static final String THEMOVIEDB_API_KEY = System.getenv("THEMOVIEDB_API_KEY");

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public Optional<String> getMoviesByTitle(String title) throws IOException {
        try {
           String string = restTemplate.exchange(
                    BASE_URL + "search/movie?api_key=" + THEMOVIEDB_API_KEY + "&query=" + title,
                    HttpMethod.GET, null,
                    new ParameterizedTypeReference<String>(){
                    }).getBody();
            JsonNode jsonNode = new ObjectMapper().readTree(string).get("results");
            return Optional.of(jsonNode.asText());
        } catch (HttpClientErrorException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Movie> getMovieById(Long id) {
        try {
            return Optional.ofNullable(restTemplate.getForEntity(
                    BASE_URL + "movie/" + id + "?api_key=" + THEMOVIEDB_API_KEY, Movie.class).getBody());
        } catch (HttpClientErrorException e) {

            return Optional.empty();
        }
    }
}
