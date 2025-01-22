package com.urantech.restapi.controller.user;

import com.urantech.restapi.rest.user.AuthRequest;
import com.urantech.restapi.rest.user.AuthResponse;
import com.urantech.restapi.service.jwt.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest req) {
        return authService.authenticate(req);
    }
}
