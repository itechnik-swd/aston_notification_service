package ru.astondevs.email;

public interface EmailService {

    void sendAccountCreatedEmail(String toEmail);

    void sendAccountDeletedEmail(String toEmail);
}
