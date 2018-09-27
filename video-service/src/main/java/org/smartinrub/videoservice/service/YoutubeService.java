package org.smartinrub.videoservice.service;

import java.util.List;

public interface YoutubeService {

    List<String> getYoutubeLinksByMovieId(final String videoId);
}
