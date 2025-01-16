package com.urantech.restapi.rest.user;

public record RegistrationRequest(
        String email,
        String password
) {
}
