package com.pet.reminder_app.database.repository;


import com.pet.reminder_app.database.model.Reminder;
import com.pet.reminder_app.dto.ReminderFilter;
import com.pet.reminder_app.dto.ReminderReadDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReminderRepository extends JpaRepository<Reminder,Long>, FilterReminderRepository{
}
