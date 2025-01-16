package com.urantech.restapi.rest.user;

public record AuthRequest(
        String email,
        String password
) {
}
