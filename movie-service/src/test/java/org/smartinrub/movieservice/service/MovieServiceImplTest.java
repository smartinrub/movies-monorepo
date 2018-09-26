package org.smartinrub.movieservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.smartinrub.movieservice.model.Movie;
import org.smartinrub.movieservice.model.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.MockRestServiceServer;

import java.io.IOException;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
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

    @AfterEach
    void tearDown() {
        server.reset();
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("getMoviesByTitle given movie title when remote client returns array of movies then returns movies")
    class getMoviesByTitle_givenMovieTitle_whenRemoteClientReturnsArrayOfMovies_thenReturnsMovies {

        String expectedMovies;

        List<Movie> movies;

        @BeforeAll
        void initAll() throws IOException {
            expectedMovies = "{\"results\":" +
                    "[{\"id\": \"226979\",\"title\": \"Test\",\"poster_path\": \"/mMohokylrZ2AsFPgqdrgI8o7i9i.jpg\",\"release_date\": \"2013-06-29\"}," +
                    "{\"id\": \"401123\",\"title\": \"Beta Test\",\"poster_path\": \"/9lvcWpo7na9nxW3FMyIQoc2tmdQ.jpg\",\"release_date\": \"2016-07-22\"}," +
                    "{\"id\": \"427557\",\"title\": \"Attitude Test\",\"poster_path\": \"/eaiGbe9dYYwXPbGAfsChKCd5rsa.jpg\",\"release_date\": \"2016-12-01\"}]}";

            server.expect(requestTo("https://api.themoviedb.org/3/search/movie?api_key=28a5b67e6420000653b42520a245e476&query=test"))
                    .andRespond(withSuccess(expectedMovies, MediaType.APPLICATION_JSON));
            movies = movieService.getMoviesByTitle("test").get();
        }

        @Nested
        class ListOfMoviesIsNotNullOrEmpty {

            @Test
            void listIsNotNull() {
                assertThat(movies).isNotNull();
            }

            @Test
            void listIsNotEmpty() {
                assertThat(movies).isNotEmpty();
            }

            @Nested
            class ListContainsMovies {

                @Test
                void listContainsThreeMovies () {
                    assertThat(movies.size()).isEqualTo(3);
                }

                @Test
                void firstMovieIsCorrect() {
                    assertAll("movie",
                            () -> assertThat(movies.get(0).getId()).isEqualTo("226979"),
                            () -> assertThat(movies.get(0).getTitle()).isEqualTo("Test"),
                            () -> assertThat(movies.get(0).getCover()).isEqualTo("/mMohokylrZ2AsFPgqdrgI8o7i9i.jpg"),
                            () -> assertThat(movies.get(0).getReleaseDate()).isEqualTo("2013-06-29")
                    );
                }
            }

        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("getMoviesByTitle given movie title when remote client returns array of movies then returns movies")
    class getMoviesByTitle_givenMovieTitle_whenRemoteClientReturnsEmptyArray_thenReturnsEmptyArray {

        String expectedMovies;

        List<Movie> movies;

        @BeforeAll
        void initAll() throws IOException {
            expectedMovies = "{\"results\":[]}";

            server.expect(requestTo("https://api.themoviedb.org/3/search/movie?api_key=28a5b67e6420000653b42520a245e476&query=test123"))
                    .andRespond(withSuccess(expectedMovies, MediaType.APPLICATION_JSON));
            movies = movieService.getMoviesByTitle("test123").get();
        }

        @Nested
        class ListOfMoviesIsNotNullOrEmpty {

            @Test
            void listIsNotNull() {
                assertThat(movies).isNotNull();
            }

            @Test
            void listIsEmpty() {
                assertThat(movies).isEmpty();
            }

            @Nested
            class ListContainsNoContainsAnyMovie {

                @Test
                void listContainsNoMovies () {
                    assertThat(movies.size()).isEqualTo(0);
                }
            }

        }
    }

    @Test
    @DisplayName("getMoviesByTitle given movie title when remote client returns array of movies then returns movies")
    void getMoviesByTitle_givenMovieTitle_whenRemoteClientReturnsNotFound_thenReturnsEmptyArray() throws IOException {
        Optional<List<Movie>> movies;
        server.expect(requestTo("https://api.themoviedb.org/3/search/movie?api_key=28a5b67e6420000653b42520a245e476&query=test123"))
                .andRespond(withStatus(HttpStatus.NOT_FOUND));
        movies = movieService.getMoviesByTitle("test123");

        assertThat(movies).isEmpty();
    }

    @Test
    @DisplayName("getMovieById given movieId when remote client returns movie then returns movie")
    void getMovieById_givenMovieId_whenRemoteClientReturnsMovie_thenReturnsMovie() throws Exception {
        Map<String, String> map = new HashMap<>();
        List<Map<String, String>> genres = new ArrayList<>();
        genres.add(map);
        Producer producer = new Producer("1", "logo", "producer", "uk");
        List<Producer> producers = new ArrayList<>();
        producers.add(producer);

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

    @Test
    @DisplayName("getMovieById given movieId when remote client returns null then returns empty")
    void getMoviesByTitle_givenMovieTitle_whenRemoteClientReturnsNotFound_thenReturnsEmpty() {
        server.expect(requestTo("https://api.themoviedb.org/3/movie/9999999?api_key=28a5b67e6420000653b42520a245e476"))
                .andRespond(withStatus(HttpStatus.NOT_FOUND));

        Optional<Movie> movie = movieService.getMovieById(9999999L);
        assertThat(movie).isEmpty();
    }

}
