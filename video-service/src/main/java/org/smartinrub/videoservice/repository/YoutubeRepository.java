package org.smartinrub.videoservice.repository;

import java.io.IOException;
import java.util.List;

public interface YoutubeRepository {

    List<String> getYoutubeLinksByMovieTitle(String movieTitle) throws IOException;
}
