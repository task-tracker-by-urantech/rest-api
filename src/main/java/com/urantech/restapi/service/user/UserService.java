package com.urantech.restapi.service.user;

import com.urantech.restapi.entity.user.User;
import com.urantech.restapi.entity.user.UserAuthority;
import com.urantech.restapi.exception.EmailExistsException;
import com.urantech.restapi.repository.user.UserAuthorityRepository;
import com.urantech.restapi.repository.user.UserRepository;
import com.urantech.restapi.rest.user.RegistrationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final UserAuthorityRepository userAuthorityRepo;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void register(RegistrationRequest req) {
        Optional<User> userOpt = userRepo.findByEmail(req.email());
        if (userOpt.isPresent()) {
            throw new EmailExistsException("User with email %s already exists".formatted(req.email()));
        }

        User user = new User(req.email(), passwordEncoder.encode(req.password()));
        UserAuthority authority = new UserAuthority(UserAuthority.Authority.USER, user);
        user.setAuthorities(Set.of(authority));

        userRepo.save(user);
        userAuthorityRepo.save(authority);

        sendNotification(user.getEmail());
    }

    private void sendNotification(String email) {
        kafkaTemplate.send("EMAIL_SENDING", email);
        log.info("Message sent to kafka");
    }
}
