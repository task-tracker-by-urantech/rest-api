package com.urantech.restapi.model.rest.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record RegistrationRequest(
        @Email
        @NotEmpty
        String email,

        @NotEmpty
        @Size(min = 6)
        String password) {
}
