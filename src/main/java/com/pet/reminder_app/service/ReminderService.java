package com.pet.reminder_app.service;

import com.pet.reminder_app.database.repository.ReminderRepository;
import com.pet.reminder_app.dto.ReminderCreateEditDTO;
import com.pet.reminder_app.dto.ReminderReadDTO;
import com.pet.reminder_app.mapper.ReminderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReminderService {

    private final ReminderRepository reminderRepository;

    private final ReminderMapper reminderMapper;


    public List<ReminderReadDTO> findAllReminders() {
        return reminderRepository.findAll().stream().map(reminderMapper::mapFromReminderToReminderReadDTO).toList();
    }


    @Transactional
    public ReminderReadDTO create(ReminderCreateEditDTO reminderCreateEditDTO) {
        var reminder = reminderRepository.save(reminderMapper.mapFromReminderCreateEditDTOToReminder(reminderCreateEditDTO));
        return reminderMapper.mapFromReminderToReminderReadDTO(reminder);
    }

    @Transactional
    public boolean delete(Long reminderId) {
        return reminderRepository.findById(reminderId)
                .map(entity -> {
                    reminderRepository.delete(entity);
                    reminderRepository.flush();
                    return true;
                })
                .orElse(false);
    }

    @Transactional
    public Optional<ReminderReadDTO> update(Long id, ReminderCreateEditDTO reminderCreateEditDTO) {
        return reminderRepository.findById(id).
                map(entity -> reminderMapper.mapFromReminderCreateEditDTOToReminder(reminderCreateEditDTO, entity))
                .map(reminderRepository::saveAndFlush)
                .map(reminderMapper::mapFromReminderToReminderReadDTO);
    }
}
