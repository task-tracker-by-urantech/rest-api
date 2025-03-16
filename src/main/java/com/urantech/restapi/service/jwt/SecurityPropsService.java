package com.urantech.restapi.service.jwt;

import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SecurityPropsService {
    private SecretKey sKey;
    private final String secretKey;

    public SecurityPropsService(@Value("${jwt.secret-key}") String secretKey) {
        this.secretKey = secretKey;
    }

    public SecretKey getSecretKey() {
        if (sKey == null) {
            sKey = Keys.hmacShaKeyFor(secretKey.getBytes());
        }
        return sKey;
    }
}
