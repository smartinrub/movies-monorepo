package org.smartinrub.videoservice.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.smartinrub.videoservice.service.VideoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(VideoController.class)
class VideoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VideoServiceImpl service;

    @Test
    void getVideosById_givenMovieIdAndMovieTitle_whenListOfLinksIsNotEmpty_thenReturnLinksAndOkResponse() throws Exception {
        List<String> links = Arrays.asList("link1", "link2", "link3");
        when(service.getVideosLink("1", "test")).thenReturn(links);
        mockMvc.perform(get("/1?title=test"))
                .andExpect(status().isOk())
                .andExpect(content().json("[\"link1\",\"link2\",\"link3\"]"));
    }

    @Test
    void getVideosById_givenMovieIdAndMissingTitle_whenListOfLinksIsNotEmpty_thenReturnBadRequestResponse() throws Exception {
        mockMvc.perform(get("/1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getVideosById_givenMovieIdAndMovieTitle_whenListOfLinksIsEmpty_thenReturnLinksAndServiceUnavailableResponse() throws Exception {
        when(service.getVideosLink("1", "test")).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/1?title=test"))
                .andExpect(status().isServiceUnavailable());
    }
}