package org.smartinrub.videoservice.service;

import org.smartinrub.videoservice.repository.VideoRepository;
import org.smartinrub.videoservice.repository.YoutubeRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


@Service
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;

    private final YoutubeRepository youtubeRepository;

    public VideoServiceImpl(VideoRepository videoRepository, YoutubeRepository youtubeRepository) {
        this.videoRepository = videoRepository;
        this.youtubeRepository = youtubeRepository;
    }

    @Override
    public List<String> getVideosLink(String movieId, String movieTitle) throws IOException {
        if (videoRepository.isVideoSaved(movieId)) {
            return videoRepository.getLinks(movieId);
        }

        List<String> links = youtubeRepository.getYoutubeLinksByMovieTitle(movieTitle);
        videoRepository.saveLinks(movieId, links);
        return links;
    }
}
