package com.pet.reminder_app.database.repository;

import com.pet.reminder_app.database.model.Reminder;
import com.pet.reminder_app.dto.ReminderFilter;

import java.util.List;


public interface FilterReminderRepository {

    List<Reminder> findAllByFilter(Long id, ReminderFilter filter);
}
