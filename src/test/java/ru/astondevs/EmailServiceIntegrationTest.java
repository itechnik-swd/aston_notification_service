package ru.astondevs;

import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.store.FolderException;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetupTest;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.astondevs.email.EmailService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(properties = {
        "spring.mail.host=localhost",
        "spring.mail.port=3025",
        "spring.mail.username=test",
        "spring.mail.password=password",
        "spring.mail.properties.mail.smtp.auth=false",
        "spring.mail.properties.mail.smtp.starttls.enable=false",
        "spring.mail.properties.mail.smtp.starttls.required=false",
        "spring.mail.properties.mail.smtp.ssl.enable=false",
        "app.email.from=test@example.com",
        "app.email.site-url=https://test.example.com"
})
class EmailServiceIntegrationTest {

    @RegisterExtension
    static GreenMailExtension greenMail = new GreenMailExtension(ServerSetupTest.SMTP)
            .withConfiguration(GreenMailConfiguration.aConfig()
                    .withUser("test", "password")
            )
            .withPerMethodLifecycle(false);

    @Autowired
    private EmailService emailService;

    @BeforeEach
    void setUp() throws FolderException {
        greenMail.purgeEmailFromAllMailboxes();
    }

    @Test
    void sendAccountCreatedEmail_ShouldSendEmail() throws Exception {
        // Given
        String toEmail = "recipient@example.com";

        // When
        emailService.sendAccountCreatedEmail(toEmail);

        // Then
        assertTrue(greenMail.waitForIncomingEmail(5000, 1));

        MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
        assertThat(receivedMessages).hasSize(1);

        MimeMessage message = receivedMessages[0];
        assertThat(message.getAllRecipients()[0]).hasToString(toEmail);

        String body = getBodyContent(message);
        assertThat(body).contains("был успешно создан");
    }

    @Test
    void sendAccountDeletedEmail_ShouldSendEmail() throws Exception {
        // Given
        String toEmail = "recipient@example.com";

        // When
        emailService.sendAccountDeletedEmail(toEmail);

        // Then
        assertTrue(greenMail.waitForIncomingEmail(5000, 1));

        MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
        assertThat(receivedMessages).hasSize(1);

        MimeMessage message = receivedMessages[0];
        assertThat(message.getAllRecipients()[0]).hasToString(toEmail);

        String subject = MimeUtility.decodeText(message.getSubject());
        assertThat(subject).contains("Аккаунт удалён");

        String body = getBodyContent(message);
        assertThat(body).contains("был удалён");
    }

    private String getBodyContent(MimeMessage message) throws Exception {
        Object content = message.getContent();

        if (content instanceof String) {
            return (String) content;
        } else if (content instanceof jakarta.mail.Multipart multipart) {
            for (int i = 0; i < multipart.getCount(); i++) {
                jakarta.mail.BodyPart bodyPart = multipart.getBodyPart(i);
                if (bodyPart.isMimeType("text/plain") || bodyPart.isMimeType("text/html")) {
                    return (String) bodyPart.getContent();
                }
            }
        }

        return GreenMailUtil.getBody(message);
    }
}
