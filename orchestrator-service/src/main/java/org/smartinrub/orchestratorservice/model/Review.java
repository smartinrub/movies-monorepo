package org.smartinrub.orchestratorservice.model;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class Review {

    @NotNull
    private final String movieId;

    @NotBlank
    @Size(min = 5, max = 100)
    private final String comment;

    @Min(1)
    @Max(5)
    @NotNull
    private final int rate;
}
