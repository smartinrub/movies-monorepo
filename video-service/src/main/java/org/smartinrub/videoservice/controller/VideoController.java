package org.smartinrub.videoservice.controller;

import org.smartinrub.videoservice.service.YoutubeServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class VideoController {

    private final YoutubeServiceImpl youtubeService;

    public VideoController(YoutubeServiceImpl youtubeService) {
        this.youtubeService = youtubeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<String>> getVideosById(@PathVariable("id") final long videoId) {
        return ResponseEntity.ok(youtubeService.getYoutubeLinksByMovieId(""));
    }
}
