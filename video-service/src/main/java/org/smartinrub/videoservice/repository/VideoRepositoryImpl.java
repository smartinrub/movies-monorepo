package org.smartinrub.videoservice.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VideoRepositoryImpl implements VideoRepository {

    private final StringRedisTemplate redisTemplate;

    @Autowired
    public VideoRepositoryImpl(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void saveLinks(String movideId, List<String> links) {
        ListOperations<String, String> stringStringListOperations = redisTemplate.opsForList();
        stringStringListOperations.leftPushAll(movideId, links);
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
