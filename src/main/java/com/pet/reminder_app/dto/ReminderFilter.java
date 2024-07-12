package com.pet.reminder_app.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record ReminderFilter(String title,
                             String description,
                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate reminder) {
}
