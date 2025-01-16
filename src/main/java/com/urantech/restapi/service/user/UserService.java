package com.urantech.restapi.service.user;

import com.urantech.restapi.exception.EmailExistsException;
import com.urantech.restapi.model.entity.User;
import com.urantech.restapi.model.rest.user.RegistrationRequest;
import com.urantech.restapi.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void register(RegistrationRequest req) {
        User user = new User(req.email(), req.password());
//                passwordEncoder.encode(req.password())
        try {
            userRepository.save(user);
        } catch (DuplicateKeyException e) {
            throw new EmailExistsException("User with email %s already exists".formatted(req.email()));
        }
//        sendNotification(req.email());
    }
}
