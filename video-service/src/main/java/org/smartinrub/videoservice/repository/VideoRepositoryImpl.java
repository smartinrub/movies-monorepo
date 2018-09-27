package org.smartinrub.videoservice.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VideoRepositoryImpl implements VideoRepository {
    @Override
    public void saveLinks(List<String> links) {

    }

    @Override
    public boolean isVideoSaved(String movieId) {
        return false;
    }

    @Override
    public List<String> getLinks(String movieId) {
        return null;
    }
}
