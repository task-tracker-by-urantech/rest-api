package com.urantech.restapi.service.user;

import com.urantech.restapi.entity.outbox.OutboxEvent;
import com.urantech.restapi.entity.user.User;
import com.urantech.restapi.entity.user.UserAuthority;
import com.urantech.restapi.exception.EmailExistsException;
import com.urantech.restapi.repository.outbox.OutboxEventRepository;
import com.urantech.restapi.repository.user.UserAuthorityRepository;
import com.urantech.restapi.repository.user.UserRepository;
import com.urantech.restapi.rest.user.RegistrationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final OutboxEventRepository outboxEventRepo;
    private final UserAuthorityRepository userAuthorityRepo;

    public void register(RegistrationRequest req) {
        final User user = createAndPersistUser(req);
        saveEvent(user.getId(), user.getEmail());
    }

    public void confirmRegistration(long userId) {
        userRepo.findById(userId).ifPresent(user -> {
            user.setStatus(User.UserStatus.ACTIVE);
            log.info("User {} activated", userId);
        });
    }

    public void compensateRegistration(long userId) {
        userRepo.deleteById(userId);
        log.warn("Compensating transaction: User {} deleted", userId);
    }

    private User createAndPersistUser(RegistrationRequest req) {
        final Optional<User> userOpt = userRepo.findByEmail(req.email());
        if (userOpt.isPresent()) {
            throw new EmailExistsException("User with email %s already exists".formatted(req.email()));
        }

        final User user = new User(req.email(), passwordEncoder.encode(req.password()));
        final UserAuthority authority = new UserAuthority(UserAuthority.Authority.USER, user);
        user.setAuthorities(Set.of(authority));
        user.setStatus(User.UserStatus.PENDING);

        userRepo.save(user);
        userAuthorityRepo.save(authority);
        return user;
    }

    private void saveEvent(long aggregateId, String payload) {
        final OutboxEvent event = OutboxEvent.builder()
                .aggregateId(aggregateId)
                .eventType("USER_REGISTERED")
                .payload(payload)
                .status(OutboxEvent.OutboxEventStatus.PENDING)
                .build();
        outboxEventRepo.save(event);
    }
}
