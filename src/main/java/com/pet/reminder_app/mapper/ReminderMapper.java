package com.pet.reminder_app.mapper;

import com.pet.reminder_app.database.model.Reminder;
import com.pet.reminder_app.dto.ReminderCreateEditDTO;
import com.pet.reminder_app.dto.ReminderReadDTO;
import com.pet.reminder_app.dto.RemindersRs;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReminderMapper {

    ReminderReadDTO mapFromReminderToReminderReadDTO(Reminder reminder);

    Reminder mapFromReminderCreateEditDTOToReminder(ReminderCreateEditDTO reminderCreateEditDTO, @MappingTarget Reminder reminder);

    Reminder mapFromReminderCreateEditDTOToReminder(ReminderCreateEditDTO reminderCreateEditDTO);

    default RemindersRs pageToResponse(Page<Reminder> page, boolean isLastPage) {
        List<ReminderReadDTO> dtoList = page.stream()
                .map(this::mapFromReminderToReminderReadDTO)
                .collect(Collectors.toList());
        return new RemindersRs(dtoList, isLastPage);
    }

}
