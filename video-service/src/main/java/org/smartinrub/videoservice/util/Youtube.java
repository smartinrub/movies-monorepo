package org.smartinrub.videoservice.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "youtube.api")
@Component
@Getter
@Setter
public class Youtube {
    private String key;
    private int maxResults;
}
