package com.pet.reminder_app.mapper;

import com.pet.reminder_app.database.model.Reminder;
import com.pet.reminder_app.dto.ReminderCreateEditDTO;
import com.pet.reminder_app.dto.ReminderReadDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReminderMapper {

    Reminder mapFromReminderReadDTOToReminder(ReminderReadDTO reminderReadDTO);

    ReminderReadDTO mapFromReminderToReminderReadDTO(Reminder reminder);

    Reminder mapFromReminderCreateEditDTOToReminder(ReminderCreateEditDTO reminderCreateEditDTO);

    Reminder mapFromReminderCreateEditDTOToReminder(ReminderCreateEditDTO reminderCreateEditDTO, @MappingTarget Reminder reminder);

}
