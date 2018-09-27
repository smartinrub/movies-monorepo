package org.smartinrub.videoservice.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class VideoRepositoryImpl implements VideoRepository {

    private final StringRedisTemplate redisTemplate;

    @Autowired
    public VideoRepositoryImpl(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void saveLinks(String movieId, List<String> links) {
        log.info("Saving links of movie {} in Redis...", movieId);
        try {
            ListOperations<String, String> stringStringListOperations = redisTemplate.opsForList();
            stringStringListOperations.leftPushAll(movieId, links);
        } catch (RuntimeException e) {
            log.error("Redis server is not responding!");
        }
    }

    @Override
    public boolean isVideoSaved(String movieId) {
        try{
            return redisTemplate.hasKey(movieId);
        } catch (RuntimeException e) {
            log.error("Redis server is not responding!");
            return false;
        }
    }

    @Override
    public List<String> getLinks(String movieId) {
        log.info("Retrieving links of movie {} in Redis...", movieId);
        ListOperations<String, String> stringStringListOperations = redisTemplate.opsForList();
        Long size = stringStringListOperations.size(movieId);
        return stringStringListOperations.range(movieId, 0, size);
    }
}
