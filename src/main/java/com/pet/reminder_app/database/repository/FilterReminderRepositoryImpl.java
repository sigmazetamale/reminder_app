package com.pet.reminder_app.database.repository;

import com.pet.reminder_app.dto.ReminderFilter;
import com.pet.reminder_app.dto.ReminderReadDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FilterReminderRepositoryImpl implements FilterReminderRepository {

    @Override
    public List<ReminderReadDTO> findAllByFilter(ReminderFilter filter) {
        return List.of();
    }
}
