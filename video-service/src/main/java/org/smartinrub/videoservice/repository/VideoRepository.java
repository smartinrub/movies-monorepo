package org.smartinrub.videoservice.repository;

import java.util.List;

public interface VideoRepository {

    void saveLinks(List<String> links);

    boolean isVideoSaved(String movieId);

    List<String> getLinks(String movieId);
}
