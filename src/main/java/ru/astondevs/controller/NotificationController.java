package ru.astondevs.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.astondevs.email.EmailService;

@Slf4j
@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final EmailService emailService;

    @PostMapping("/email/account-created")
    public ResponseEntity<String> sendAccountCreatedEmail(@RequestParam String toEmail) {
        log.info("Sending account creation email to: {}", toEmail);

        emailService.sendAccountCreatedEmail(toEmail);

        return ResponseEntity.ok("Account creation email sent successfully");
    }

    @PostMapping("/email/account-deleted")
    public ResponseEntity<String> sendAccountDeletedEmail(@RequestParam String toEmail) {
        log.info("Sending account deletion email to: {}", toEmail);

        emailService.sendAccountDeletedEmail(toEmail);

        return ResponseEntity.ok("Account deletion email sent successfully");
    }
}
