package com.urantech.restapi.model.rest.task;

import jakarta.validation.constraints.NotEmpty;

public record TaskPayloadRequest(@NotEmpty String description) {
}
