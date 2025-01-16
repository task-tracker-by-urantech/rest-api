package com.urantech.restapi.controller.user;

import com.urantech.restapi.model.rest.user.RegistrationRequest;
import com.urantech.restapi.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public void register(@RequestBody @Valid RegistrationRequest req) {
        userService.register(req);
    }
}
