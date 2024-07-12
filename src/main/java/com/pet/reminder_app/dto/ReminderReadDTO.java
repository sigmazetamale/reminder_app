package com.pet.reminder_app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record ReminderReadDTO (Long id,
                               String title,
                               String description,
                               LocalDateTime remind,
                               UserReadDTO user) {
}
