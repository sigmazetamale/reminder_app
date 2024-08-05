package com.pet.reminder_app.integration.service;

import com.pet.reminder_app.annotation.IT;
import com.pet.reminder_app.dto.*;
import com.pet.reminder_app.service.ReminderService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.test.context.TestConstructor;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@IT
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class ReminderServiceIT {

    private final ReminderService reminderService;

    private OAuth2User principal;


    private OAuth2User createPrincipal() {
        Map<String, Object> attributes = Map.of(
                "id", 1,
                "email", "morozow.cult@@gmail.com",
                "chatId", "545226378"
        );
        return new DefaultOAuth2User(Collections.emptyList(), attributes, "email");
    }

    @BeforeEach
    public void setUpPrincipal() {
        principal = createPrincipal();
    }

    @Test
    void findAllRemindersWithFilter_TestIT() {
        List<ReminderReadDTO> reminderReadDTOS = reminderService.findAllReminders(principal, new ReminderFilter("тест2", null, null, null));
        assertEquals(reminderReadDTOS,
                List.of(new ReminderReadDTO(2L, "тест2", "тест2",  LocalDateTime.of(2024, 10, 17, 10, 0, 0, 0),
                new UserReadDTO(1L, "morozow.cult@@gmail.com"))));
    }

    @Test
    void findAllRemindersWithPagination_TestIT() {
        RemindersRs remindersRs = reminderService.findAllReminders(principal, 1,1);
        assertEquals(remindersRs,
                new RemindersRs(List.of(new ReminderReadDTO(2L, "тест2", "тест2",  LocalDateTime.of(2024, 10, 17, 10, 0, 0, 0),
                new UserReadDTO(1L, "morozow.cult@@gmail.com"))), false));
    }

    @Test
    void create_TestIT() {
        ReminderCreateEditDTO reminderCreateEditDTO = new ReminderCreateEditDTO("тест2", "тест2",  LocalDateTime.of(2024, 10, 17, 10, 0, 0, 0));
        ReminderReadDTO reminderReadDTO = reminderService.create(principal, reminderCreateEditDTO);
        assertEquals(reminderReadDTO.getTitle(), reminderCreateEditDTO.getTitle());
        assertEquals(reminderReadDTO.getDescription(), reminderCreateEditDTO.getDescription());
        assertEquals(reminderReadDTO.getRemind(), reminderCreateEditDTO.getRemind());
    }

    @Test
    void delete_TestIT() {
        assertFalse(reminderService.delete(principal, 999L));
        assertTrue(reminderService.delete(principal, 1L));
    }

}
