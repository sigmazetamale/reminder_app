package com.pet.reminder_app.service;

import com.pet.reminder_app.database.model.Reminder;
import com.pet.reminder_app.database.model.User;
import com.pet.reminder_app.database.repository.ReminderRepository;
import com.pet.reminder_app.database.repository.UserRepository;
import com.pet.reminder_app.dto.*;
import com.pet.reminder_app.mapper.ReminderMapper;
import com.pet.reminder_app.util.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReminderService {

    private final ReminderRepository reminderRepository;
    private final UserRepository userRepository;

    private final ReminderMapper reminderMapper;
    private final UserService userService;


    public RemindersRs findAllReminders(Long userId, int pageNumber, int pageSize) {
        User user = userService.findById(userId);
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Reminder> page = reminderRepository.findAllByUser(user, pageable);
        return reminderMapper.pageToResponse(page, page.isLast());

    }

    public List<ReminderReadDTO> findAllSortedReminders(SortedRemindersRq sortedRemindersRq) {
        User user = userService.findById(sortedRemindersRq.getUserId());

        List<Reminder> reminderList;

        if (sortedRemindersRq.isSortedByName()) {
            reminderList = reminderRepository.findAllByUser(user, Sort.by("title")
                    .ascending());
        } else if (sortedRemindersRq.isSortedByDate()) {
            reminderList = reminderRepository.findAllByUser(user).stream()
                    .sorted(Comparator.comparing((Reminder r) -> r.getRemind().toLocalDate())).toList();
        } else if (sortedRemindersRq.isSortedByTime()) {
            reminderList = reminderRepository.findAllByUser(user).stream()
                    .sorted(Comparator.comparing((Reminder r) -> r.getRemind().toLocalTime())).toList();
        } else {
            reminderList = reminderRepository.findAllByUser(user);
        }

        return reminderList.stream().map(reminderMapper::mapFromReminderToReminderReadDTO).collect(Collectors.toList());

    }

    public List<ReminderReadDTO> findAllReminders(ReminderFilter reminderFilter) {
        userService.findById(reminderFilter.getUserId());
        return reminderRepository.findAllByFilter(reminderFilter).stream().map(reminderMapper::mapFromReminderToReminderReadDTO).toList();

    }


    @Transactional
    public ReminderReadDTO create(ReminderCreateEditDTO reminderCreateEditDTO) {
        Reminder reminder = reminderMapper.mapFromReminderCreateEditDTOToReminder(reminderCreateEditDTO);
        reminder.setUser(userRepository.findById(reminderCreateEditDTO.getUserId()).orElseThrow(UserNotFoundException::new));
        reminderRepository.save(reminder);
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
                map(entity -> {
                    Reminder reminder = reminderMapper.mapFromReminderCreateEditDTOToReminder(reminderCreateEditDTO, entity);
                    reminder.setUser(userRepository.findById(reminderCreateEditDTO.getUserId()).orElseThrow(UserNotFoundException::new));
                    return reminder;
                })
                .map(reminderRepository::saveAndFlush)
                .map(reminderMapper::mapFromReminderToReminderReadDTO);
    }
}
