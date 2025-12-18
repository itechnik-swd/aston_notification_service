package ru.astondevs.email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.astondevs.exception.EmailSendingException;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.email.from}")
    private String fromEmail;

    @Value("${app.email.site-url}")
    private String siteUrl;

    public void sendAccountCreatedEmail(String toEmail) {
        String subject = "Аккаунт создан";
        String text = String.format("Здравствуйте!%n%n"
                + "Ваш аккаунт на сайте %s был успешно создан.", siteUrl);

        sendEmail(toEmail, subject, text);
        log.info("Account creation email sent to: {}", toEmail);
    }

    public void sendAccountDeletedEmail(String toEmail) {
        String subject = "Аккаунт удалён";
        String text = String.format("Здравствуйте!%n%n"
                + "Ваш аккаунт был удалён.");

        sendEmail(toEmail, subject, text);
        log.info("Account deletion email sent to: {}", toEmail);
    }


    private void sendEmail(String toEmail, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject(subject);
            message.setText(text);

            mailSender.send(message);
            log.debug("Email successfully sent to: {}", toEmail);
        } catch (Exception e) {
            log.error("Failed to send email to: {}", toEmail, e);
            throw new EmailSendingException("Failed to send email to: " + toEmail, e);
        }
    }
}