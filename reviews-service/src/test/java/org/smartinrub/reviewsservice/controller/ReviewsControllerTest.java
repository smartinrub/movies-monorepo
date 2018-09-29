package org.smartinrub.reviewsservice.controller;

import com.mongodb.MongoTimeoutException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.smartinrub.reviewsservice.model.Review;
import org.smartinrub.reviewsservice.repository.ReviewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(ReviewsController.class)
class ReviewsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewsRepository service;

    private String payload;

    @BeforeEach
    void init() {
        payload= "{\n" +
                "\t\"movieId\":\"2\",\n" +
                "\t\"comment\":\"excellent\",\n" +
                "\t\"rate\":\"4\"\n" +
                "}";
    }

    @Test
    void addReview_givenReview_whenMongoDbServiceResponds_thenReturnsCreatedStatus() throws Exception {
        mockMvc.perform(post("/").content(payload).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void addReview_givenReview_whenMongoDbServiceDoesNotRespond_thenReturnsServiceUnavailableStatus() throws Exception {
        when(service.save(any())).thenThrow(MongoTimeoutException.class);
        mockMvc.perform(post("/").content(payload).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isServiceUnavailable());
    }

    @Test
    void addReview_givenWrongReviewFields_whenMongoDbServiceResponds_thenReturnsBadRequestStatus() throws Exception {
        String wrongPayload = "{\n" +
                "\t\"comment\":\"excellent\",\n" +
                "\t\"rate\":\"4\"\n" +
                "}";
        mockMvc.perform(post("/").content(wrongPayload).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllReviewsByMovieId_givenMovieId_whenMongoDbServiceResponds_thenReturnsListOfReviewsAndOkStatus() throws Exception {
        when(service.findAllByMovieId(anyString()))
                .thenReturn(Arrays.asList(
                        new Review("1", "comment", 3),
                        new Review("2", "comment", 5)));
        mockMvc.perform(get("/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllReviewsByMovieId_givenMovieId_whenMongoDbServiceDoesNotResponds_thenReturnsServiceUnavailableStatus() throws Exception {
        when(service.findAllByMovieId(anyString())).thenThrow(MongoTimeoutException.class);
        mockMvc.perform(get("/1"))
                .andExpect(status().isServiceUnavailable());
    }

    @Test
    void getAllReviewsByMovieId_givenMovieId_whenMongoDbServiceRespondsAndMovieIdNotFound_thenReturnNotFoundStatus() throws Exception {
        when(service.findAllByMovieId(anyString())).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/1"))
                .andExpect(status().isNotFound());
    }

}