package com.urantech.restapi.controller.user;

import com.urantech.restapi.entity.user.User;
import com.urantech.restapi.rest.user.RegistrationRequest;
import com.urantech.restapi.rest.user.UserResponse;
import com.urantech.restapi.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public void register(@RequestBody RegistrationRequest req) {
        userService.register(req);
    }

    @GetMapping("/user")
    public UserResponse getUser() {
        User user = userService.getCurrentUser();
        return new UserResponse(user.getEmail());
    }
}
