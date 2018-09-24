package org.smartinrub.movieservice.service;

import org.smartinrub.movieservice.model.Movie;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {

    private static final String BASE_URL = "https://api.themoviedb.org/3/";

    private static final String THEMOVIEDB_API_KEY = System.getenv("THEMOVIEDB_API_KEY");

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public Optional<List<Movie>> getMoviesByTitle(String title) {
        try {
            return Optional.ofNullable(restTemplate.exchange(
                    BASE_URL + "search/movie?api_key=" + THEMOVIEDB_API_KEY + "&query=" + title,
                    HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<Movie>>() {
                    }).getBody());
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
