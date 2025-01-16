package com.urantech.restapi.model.rest.task;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.lang.Nullable;

public record TaskUpdateRequest(
        long id,
        @NotEmpty
        String description,
        @Nullable
        Boolean done
) {
}
