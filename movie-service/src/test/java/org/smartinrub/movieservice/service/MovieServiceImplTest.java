package org.smartinrub.movieservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.smartinrub.movieservice.model.Movie;
import org.smartinrub.movieservice.model.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.HttpClientErrorException;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withNoContent;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@ExtendWith(SpringExtension.class)
@RestClientTest(MovieServiceImpl.class)
class MovieServiceImplTest {

    @Autowired
    private MovieServiceImpl movieService;

    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("getMovieById given movieId when remote client returns movie then returns movie")
    @Test
    void getMovieById_givenMovieId_whenRemoteClientReturnsMovie_thenReturnsMovie() throws Exception {
        Map<String, String> map = new HashMap<>();
        List<Map<String, String>> genres = new ArrayList<>();
        genres.add(map);
        List<Producer> producers = new ArrayList<>();

        String expectedMovie =
                objectMapper.writeValueAsString(new Movie(
                        "1",
                        "scary app",
                        "mypath.com",
                        "01/01/1991",
                        genres,
                        producers));
        server.expect(requestTo("https://api.themoviedb.org/3/movie/1?api_key=28a5b67e6420000653b42520a245e476"))
                .andRespond(withSuccess(expectedMovie, MediaType.APPLICATION_JSON));

        Movie movie = movieService.getMovieById(1L).get();

        assertAll("movie",
                () -> assertThat(movie.getTitle()).isEqualTo("scary app"),
                () -> assertThat(movie.getCover()).isEqualTo("mypath.com"),
                () -> assertThat(movie.getReleaseDate()).isEqualTo("01/01/1991"),
                () -> assertThat(movie.getGenres()).isNotNull(),
                () -> assertThat(movie.getProducers()).isNotNull());
    }

    @DisplayName("getMovieById given movieId when remote client returns null then returns empty")
    @Test
    void getMoviesByTitle_givenMovieTitle_whenRemoteClientDoesNotReturnMovie_thenReturnsEmpty() {
        server.expect(requestTo("https://api.themoviedb.org/3/movie/9999999?api_key=28a5b67e6420000653b42520a245e476"))
                .andRespond(withStatus(HttpStatus.NOT_FOUND));

        Optional<Movie> movie = movieService.getMovieById(9999999L);
        assertThat(movie).isEmpty();
    }

}
