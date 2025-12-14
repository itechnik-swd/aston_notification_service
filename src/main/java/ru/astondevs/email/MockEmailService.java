package ru.astondevs.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev") // Только для профиля dev
@Slf4j
public class MockEmailService {

    public void sendAccountCreatedEmail(String toEmail) {
        log.info("""
            ===== MOCK EMAIL SENT =====
            To: {}
            Subject: Добро пожаловать!
            Message: Здравствуйте! Ваш аккаунт был успешно создан.
            ============================
            """, toEmail);
    }

    public void sendAccountDeletedEmail(String toEmail) {
        log.info("""
            ===== MOCK EMAIL SENT =====
            To: {}
            Subject: Ваш аккаунт был удалён
            Message: Здравствуйте! Ваш аккаунт был удалён.
            ============================
            """, toEmail);
    }
}