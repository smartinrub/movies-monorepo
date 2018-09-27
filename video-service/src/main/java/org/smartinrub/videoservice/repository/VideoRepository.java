package org.smartinrub.videoservice.repository;

import java.net.ConnectException;
import java.util.List;

public interface VideoRepository {

    void saveLinks(String movieId, List<String> links);

    boolean isVideoSaved(String movieId) throws ConnectException;

    List<String> getLinks(String movieId);
}
