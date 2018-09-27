package org.smartinrub.videoservice.service;

import java.io.IOException;
import java.util.List;

public interface VideoService {

    List<String> getVideosLink(final String movideId, final String videoId) throws IOException;
}
