package org.smartinrub.movieservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.smartinrub.movieservice.model.Movie;
import org.smartinrub.movieservice.model.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.MockRestServiceServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
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

    @BeforeEach
    void setUp() throws Exception {
        Map<String, String> map = new HashMap<>();
        List<Map<String, String>> genres = new ArrayList<>();
        genres.add(map);
        List<Producer> producers = new ArrayList<>();

        String movie =
                objectMapper.writeValueAsString(new Movie(
                        "1",
                        "scary app",
                        "mypath.com",
                        "01/01/1991",
                        genres,
                        producers));

        this.server.expect(requestTo("https://api.themoviedb.org/3/movie/1?api_key=28a5b67e6420000653b42520a245e476"))
                .andRespond(withSuccess(movie, MediaType.APPLICATION_JSON));
    }

    @Test
    void getMoviesByTitle_givenMovieTitle_when() {
        Movie movie = movieService.getMovieById(1L).get();

        assertThat(movie.getTitle()).isEqualTo("scary app");
        assertThat(movie.getCover()).isEqualTo("mypath.com");
        assertThat(movie.getReleaseDate()).isEqualTo("01/01/1991");
        assertThat(movie.getGenres()).isNotNull();
        assertThat(movie.getProducers()).isNotNull();
    }

}
