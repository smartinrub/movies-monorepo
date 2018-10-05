package org.smartinrub.videoservice.service;

import java.io.IOException;
import java.util.List;

public interface VideoService {

    List<String> getVideosLink(String movieId, String videoId) throws IOException;
}
