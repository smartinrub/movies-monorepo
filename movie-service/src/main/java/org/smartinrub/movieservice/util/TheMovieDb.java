package org.smartinrub.movieservice.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "themoviedb.api")
@Component
@Getter
@Setter
public class TheMovieDb {
    private String key;
}
