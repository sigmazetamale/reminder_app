package com.pet.reminder_app.database.repository;

import com.pet.reminder_app.dto.ReminderFilter;
import com.pet.reminder_app.dto.ReminderReadDTO;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface FilterReminderRepository {
    List<ReminderReadDTO> findAllByFilter(ReminderFilter filter);
}
