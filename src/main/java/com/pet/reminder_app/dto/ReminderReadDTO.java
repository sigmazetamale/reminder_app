package com.pet.reminder_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ReminderReadDTO{

    private Long id;

    private String title;

    private String description;

    private LocalDateTime remind;

    private UserReadDTO user;

}
