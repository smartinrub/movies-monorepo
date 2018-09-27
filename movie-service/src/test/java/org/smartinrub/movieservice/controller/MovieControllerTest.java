package org.smartinrub.movieservice.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.smartinrub.movieservice.model.Movie;
import org.smartinrub.movieservice.model.Producer;
import org.smartinrub.movieservice.service.MovieServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MovieController.class)
class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieServiceImpl service;

    @Test
    void getMoviesByTitle_givenMovieTitle_whenListOfMoviesIsPresent_thenReturnsMoviesAndOkResponse() throws Exception {
        when(service.getMoviesByTitle("test")).thenReturn(Optional.of(anyList()));
        mockMvc.perform(get("/test"))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @Test
    void getMoviesByTitle_givenMovieTitle_whenMoviesIsNotPresent_thenReturnsNotFoundResponse() throws Exception {
        when(service.getMoviesByTitle("test")).thenReturn(Optional.empty());
        mockMvc.perform(get("/test"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getMovieById_givenMovieId_whenMovieIsPresent_thenReturnsMovieAndOkResponse() throws Exception {
        Map<String, String> map = new HashMap<>();
        List<Map<String, String>> genres = new ArrayList<>();
        genres.add(map);
        List<Producer> producers = new ArrayList<>();

        when(service.getMovieById(1L)).thenReturn(Optional.of(new Movie(
                "1" ,
                "scary app" ,
                "mypath.com" ,
                "01/01/1991" ,
                genres,
                producers)));

        mockMvc.perform(get("/movie/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":\"1\"," +
                        "\"title\":\"scary app\"," +
                        "\"genres\":[{}]," +
                        "\"poster_path\":\"mypath.com\"," +
                        "\"release_date\":\"01/01/1991\"," +
                        "\"production_companies\":[]}"));
    }

    @Test
    void getMovieById_givenMovieId_whenMovieIsNotPresent_thenReturnsNotFoundResponse() throws Exception {
        when(service.getMovieById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/movie/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getMovieById_givenWrongMovieIdFormat_thenReturnsBadRequestResponse() throws Exception {
        mockMvc.perform(get("/movie/abc"))
                .andExpect(status().isBadRequest());
    }

}