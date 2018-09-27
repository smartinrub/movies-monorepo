package org.smartinrub.videoservice.repository;

import java.util.List;

public interface VideoRepository {

    void saveLinks(String movieId, List<String> links);

    boolean isVideoSaved(String movieId);

    List<String> getLinks(String movieId);
}
