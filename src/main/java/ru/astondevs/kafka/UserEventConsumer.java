package ru.astondevs.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.astondevs.email.MockEmailService;
import ru.astondevs.kafka.event.UserEvent;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserEventConsumer { //UserEventListener

    private final MockEmailService mockEmailService;

    @KafkaListener(topics = "${spring.kafka.consumer.topics}",
            groupId = "${spring.kafka.consumer.group-id}")
    public void consumeUserEvent(UserEvent event) {
        log.info("Received user event: {} for user: {}", event.eventType(), event.email());

        try {
            switch (event.eventType()) {
              case USER_CREATED:
                  mockEmailService.sendAccountCreatedEmail(event.email());
                  log.info("Account created email sent to: {}", event.email());
                  break;
              case USER_DELETED:
                  mockEmailService.sendAccountDeletedEmail(event.email());
                  log.info("Account deleted email sent to: {}", event.email());
                  break;
              default:
                  log.warn("Unknown event type: {}", event.eventType());
            }
        } catch (Exception e) {
            log.error("Error processing user event for email: {}", event.email(), e);
        }
    }
}
