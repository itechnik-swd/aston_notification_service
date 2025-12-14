package ru.astondevs.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class EmailConfig {

    @Bean
    public MockEmailService mockMailSender() {
        log.info("Creating mock JavaMailSender");
        return new MockEmailService();
    }
}
