package ru.astondevs.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
@Slf4j
public class MockEmailService implements EmailService {

    public void sendAccountCreatedEmail(String toEmail) {
        log.info("""
            ===== MOCK EMAIL SENT =====
            To: {}
            Subject: Аккаунт создан.
            Message: Здравствуйте! Ваш аккаунт был успешно создан.
            ============================
            """, toEmail);
    }

    public void sendAccountDeletedEmail(String toEmail) {
        log.info("""
            ===== MOCK EMAIL SENT =====
            To: {}
            Subject: Аккаунт удалён.
            Message: Здравствуйте! Ваш аккаунт на сайте www.ваш.сайт был удалён.
            ============================
            """, toEmail);
    }
}