package com.pet.reminder_app.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record ReminderCreateEditDTO (@NotBlank String title,
                                     String description,
                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime remind,
                                     Long userId){
}
