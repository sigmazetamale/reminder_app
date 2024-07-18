package com.pet.reminder_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RemindersRs {

    private List<ReminderReadDTO> reminders;

    private boolean isLast;

}
