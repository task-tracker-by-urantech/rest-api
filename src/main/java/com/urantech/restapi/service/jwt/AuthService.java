package com.urantech.restapi.service.jwt;

import com.urantech.restapi.rest.user.AuthRequest;
import com.urantech.restapi.rest.user.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    public AuthResponse authenticate(AuthRequest req) {
        String email = req.email().trim().toLowerCase();
        Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(email, req.password()));
        String token = jwtService.generateToken(auth);
        return new AuthResponse(token);
    }
}
