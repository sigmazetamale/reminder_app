package com.pet.reminder_app.service;

import com.pet.reminder_app.database.model.Reminder;
import com.pet.reminder_app.database.model.User;
import com.pet.reminder_app.database.repository.ReminderRepository;
import com.pet.reminder_app.dto.*;
import com.pet.reminder_app.mapper.ReminderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReminderService {

    private final ReminderRepository reminderRepository;

    private final ReminderMapper reminderMapper;
    private final UserService userService;
    private final EmailService emailService;
    private final TelegramBotService telegramBotService;

    @Scheduled(fixedDelay = 1000)
    public void sendReminders() throws InterruptedException {
        List<Reminder> reminders = reminderRepository.findAllByRemindAfter(LocalDateTime.now());
        for (Reminder reminder : reminders) {
//            sendEmail(reminder);
            if (reminder.getUser().getChatId() != null){
                sendTelegramMessage(reminder);
                Thread.sleep(2000);
            }
        }
    }

    private void sendEmail(Reminder reminder) {
        String toAddress = reminder.getUser().getEmail();
        String text = "Напоминание " + reminder.getTitle()  + "/n"
                + "Описание: " + reminder.getDescription()
                + " Время напоминания: " + reminder.getRemind();
        emailService.sendSimpleMessage(toAddress, text);
    }

    private void sendTelegramMessage(Reminder reminder) {
        SendMessage message = new SendMessage();
        message.setChatId(reminder.getUser().getChatId());
        message.setText("Reminder: " + reminder.getTitle() + "\n" + "Описание: " + reminder.getDescription() + "\n" + "for user: " + reminder.getUser().getEmail());
        try {
            telegramBotService.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    public RemindersRs findAllReminders(OAuth2User authentication, int pageNumber, int pageSize) {
        User user = userService.findByEmail(authentication.getAttribute("email"));
        Pageable pageable = PageRequest.of(pageNumber-1, pageSize);
        Page<Reminder> page = reminderRepository.findAllByUser(user, pageable);
        return reminderMapper.pageToResponse(page, page.isLast());

    }

    public List<ReminderReadDTO> findAllReminders(OAuth2User authentication, ReminderFilter reminderFilter) {
        User user = userService.findByEmail(authentication.getAttribute("email"));
        return reminderRepository.findAllByFilter(user.getId(), reminderFilter).stream().map(reminderMapper::mapFromReminderToReminderReadDTO).toList();

    }

    public List<ReminderReadDTO> findAllSortedReminders(OAuth2User authentication, SortedRemindersRq sortedRemindersRq) {
        User user = userService.findByEmail(authentication.getAttribute("email"));

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


    @Transactional
    public ReminderReadDTO create(OAuth2User authentication, ReminderCreateEditDTO reminderCreateEditDTO) {
        Reminder reminder = reminderMapper.mapFromReminderCreateEditDTOToReminder(reminderCreateEditDTO);
        User user = userService.findByEmail(authentication.getAttribute("email"));
        reminder.setUser(user);
        reminderRepository.save(reminder);
        return reminderMapper.mapFromReminderToReminderReadDTO(reminder);
    }

    @Transactional
    public boolean delete(OAuth2User authentication, Long reminderId) {
        User user = userService.findByEmail(authentication.getAttribute("email"));
        return reminderRepository.findByIdAndUser(reminderId, user)
                .map(entity -> {
                    reminderRepository.delete(entity);
                    reminderRepository.flush();
                    return true;
                })
                .orElse(false);
    }

    @Transactional
    public Optional<ReminderReadDTO> update(OAuth2User authentication, Long id, ReminderCreateEditDTO reminderCreateEditDTO) {
        User user = userService.findByEmail(authentication.getAttribute("email"));
        return reminderRepository.findByIdAndUser(id, user).
                map(entity -> {
                    Reminder reminder = reminderMapper.mapFromReminderCreateEditDTOToReminder(reminderCreateEditDTO, entity);
                    return reminder;
                })
                .map(reminderRepository::saveAndFlush)
                .map(reminderMapper::mapFromReminderToReminderReadDTO);
    }
}
