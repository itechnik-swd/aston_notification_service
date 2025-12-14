package ru.astondevs.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmailRequestDto(
        @NotBlank(message = "Email is required")
        @Email(message = "Email should be valid")
        String toEmail,

        @NotBlank(message = "Subject is required")
        String subject,

        @NotBlank(message = "Text is required")
        String text
) {
}
