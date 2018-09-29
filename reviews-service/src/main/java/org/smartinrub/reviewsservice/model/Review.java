package org.smartinrub.reviewsservice.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;


@Data
@Document(collection = "reviews")
public class Review {

    @NotNull
    @Field("movieid")
    private final String movieId;

    @NotBlank
    @Size(min = 5, max = 100)
    private final String comment;

    @Min(1)
    @Max(5)
    @NotNull
    private final int rate;
}
