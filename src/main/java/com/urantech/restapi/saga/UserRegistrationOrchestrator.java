package com.urantech.restapi.saga;

import com.urantech.restapi.repository.outbox.OutboxEventRepository;
import com.urantech.restapi.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.urantech.restapi.entity.outbox.OutboxEvent.OutboxEventStatus.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserRegistrationOrchestrator {
    private final UserService userService;
    private final OutboxEventRepository outboxEventRepo;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Transactional
    @Scheduled(fixedDelay = 5000)
    public void processOutbox() {
        outboxEventRepo.findByStatus(PENDING).forEach(event -> {
            try {
                kafkaTemplate.send("USER_REGISTRATION", event.getPayload()).get();
                event.setStatus(SENT);
                outboxEventRepo.save(event);
                userService.confirmRegistration(event.getAggregateId());
                log.info("Message sent to kafka");
            } catch (Exception e) {
                log.error("Failed to send event {}", event.getId(), e);
                userService.compensateRegistration(event.getAggregateId());
                event.setStatus(FAILED);
                outboxEventRepo.save(event);
            }
        });
    }
}
