package org.smartinrub.videoservice.controller;

import org.smartinrub.videoservice.service.VideoServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.List;

@RestController
public class VideoController {

    private final VideoServiceImpl youtubeService;

    public VideoController(VideoServiceImpl youtubeService) {
        this.youtubeService = youtubeService;
    }

    @GetMapping("/{movie_id}")
    public ResponseEntity<List<String>> getVideosById(@PathVariable("movie_id") final long movieId,
                                                      @RequestParam("title") final String movieTitle) throws IOException {
        return ResponseEntity.ok(youtubeService.getVideosLink(Long.toString(movieId), movieTitle));
    }
}
