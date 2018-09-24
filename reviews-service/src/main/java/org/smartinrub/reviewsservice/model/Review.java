package org.smartinrub.reviewsservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
public class Review {

    @Id
    private final int movieId;

    private final String comment;
    private final int rate;
}
