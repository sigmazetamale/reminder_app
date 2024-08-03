package com.pet.reminder_app.controllers;


import com.pet.reminder_app.dto.*;
import com.pet.reminder_app.http.controller.ReminderController;
import com.pet.reminder_app.service.ReminderService;
import com.pet.reminder_app.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReminderControllerTest {

    @Mock
    private ReminderService reminderService;

    @Mock
    private UserService userService;

    @InjectMocks
    private ReminderController reminderController;

    private OAuth2User principal;


    private OAuth2User createPrincipal() {
        Map<String, Object> attributes = Map.of(
                "id", 1,
                "email", "test@mail.com",
                "chatId", "123456"
        );
        return new DefaultOAuth2User(Collections.emptyList(), attributes, "email");
    }

    @BeforeEach
    public void setUpPrincipal() {
        principal = createPrincipal();
    }

    /**
     * Тест на API /list
     */
    @Test
    void findAllReminders_ReturnsValidResponseEntity() {

        List<ReminderReadDTO> expectedRemindersPage1 = List.of(
                new ReminderReadDTO(1L, "test title 1", "test description 1", LocalDateTime.now(),
                        new UserReadDTO(1L, "test email 1"))
        );

        when(reminderService.findAllReminders(principal, 1, 1)).thenReturn(new RemindersRs(expectedRemindersPage1,false));

        ResponseEntity<RemindersRs> response1 = reminderController.findAllReminders(new RemindersListRq(1, 1), principal);

        verify(reminderService, times(1)).findAllReminders(principal, 1, 1);
        assertEquals(HttpStatus.OK, response1.getStatusCode());
        assertEquals(expectedRemindersPage1, response1.getBody().getReminders());
        assertEquals(false, response1.getBody().isLast());

        List<ReminderReadDTO> expectedRemindersPage2 = List.of(
                new ReminderReadDTO(1L, "test title 1", "test description 1", LocalDateTime.now(),
                        new UserReadDTO(1L, "test email 1")),
                new ReminderReadDTO(2L, "test title 2", "test description 2", LocalDateTime.now().plusDays(1),
                        new UserReadDTO(2L, "test email 2"))
        );

        when(reminderService.findAllReminders(principal, 1, 2)).thenReturn(new RemindersRs(expectedRemindersPage2,true));

        ResponseEntity<RemindersRs> response2 = reminderController.findAllReminders(new RemindersListRq(1, 2), principal);

        verify(reminderService, times(1)).findAllReminders(principal, 1, 2);
        assertEquals(HttpStatus.OK, response2.getStatusCode());
        assertEquals(expectedRemindersPage2, response2.getBody().getReminders());
        assertEquals(true, response2.getBody().isLast());

    }

    /**
     * Тест на API /create
     */
    @Test
    void createReminder_ReturnsValidResponseEntity() {

        ReminderCreateEditDTO reminderCreateEditDTO = new ReminderCreateEditDTO("test title 1", "test description 1", LocalDateTime.now());

        ReminderReadDTO reminderReadDTO = new ReminderReadDTO(1L, "test title 1", "test description 1", LocalDateTime.now(),
                new UserReadDTO(1L, "test email 1"));
        when(reminderService.create(principal, reminderCreateEditDTO)).thenReturn(reminderReadDTO);

        ResponseEntity<ReminderReadDTO> response = reminderController.create(reminderCreateEditDTO, principal);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(reminderReadDTO, response.getBody());
        verify(reminderService, times(1)).create(principal, reminderCreateEditDTO);
    }

    /**
     * Тест на API /update
     */
    @Test
    void updateReminder_ReturnsValidResponseEntityOrThrowsException() {

        ReminderCreateEditDTO reminderCreateEditDTO = new ReminderCreateEditDTO("test title 1", "test description 1", LocalDateTime.now());

        ReminderReadDTO reminderReadDTO = new ReminderReadDTO(1L, "test title 1", "test description 1", LocalDateTime.now(),
                new UserReadDTO(1L, "test email 1"));

        when(reminderService.update(principal, 1L, reminderCreateEditDTO)).thenReturn(Optional.of(reminderReadDTO));

        ResponseEntity<ReminderReadDTO> response = reminderController.update(1L, reminderCreateEditDTO, principal);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reminderReadDTO, response.getBody());
        verify(reminderService, times(1)).update(principal, 1L, reminderCreateEditDTO);


        when(reminderService.update(principal, 1L, reminderCreateEditDTO)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            reminderController.update( 1L, reminderCreateEditDTO, principal);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        verify(reminderService, times(2)).update(principal, 1L, reminderCreateEditDTO);
    }


    /**
     * Тест на API /delete
     */
    @Test
    void deleteReminder_ReturnsCorrectStatusOrThrowsException() {

        when(reminderService.delete(principal, 1L)).thenReturn(true);

        ResponseEntity<HttpStatus> response1 = reminderController.delete(1L, principal);

        assertEquals(HttpStatus.NO_CONTENT, response1.getStatusCode());
        verify(reminderService, times(1)).delete(principal, 1L);

        when(reminderService.delete(principal, 1L)).thenReturn(false);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            reminderController.delete(1L, principal);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        verify(reminderService, times(2)).delete(principal, 1L);

    }

    /**
     * Тест на API /sort
     */
    @Test
    void findAllSortedReminders_WhenSortByName_ReturnsSortedByName() {


        List<ReminderReadDTO> sortedByNameReminder = List.of(new ReminderReadDTO(1L, "test title 1", "test description 1", LocalDateTime.now(),
                new UserReadDTO(1L, "test email 1")));

        SortedRemindersRq sortedRemindersRq = new SortedRemindersRq(true, false,false);

        when(reminderService.findAllSortedReminders(principal, sortedRemindersRq))
                .thenReturn(sortedByNameReminder);

        ResponseEntity<List<ReminderReadDTO>> response = reminderController.findAllSortedReminders(sortedRemindersRq, principal);

        verify(reminderService, times(1)).findAllSortedReminders(principal, sortedRemindersRq);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sortedByNameReminder, response.getBody());
    }

    /**
     * Тест на API /filtr
     */
    @Test
    void filterReminder_WhenDate_ReturnsFilteredByDate() {

        ReminderFilter filterRequest = new ReminderFilter("test title", null, null, null);

        List<ReminderReadDTO> filteredByTitleReminders = List.of(new ReminderReadDTO(1L, "test title 1", "test description 1", LocalDateTime.now(),
                new UserReadDTO(1L, "test email 1")));

        when(reminderService.findAllReminders(principal, filterRequest)).thenReturn(filteredByTitleReminders);

        ResponseEntity<List<ReminderReadDTO>> response = reminderController.findAllFilteredReminders(filterRequest, principal);

        verify(reminderService, times(1)).findAllReminders(principal, filterRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(filteredByTitleReminders, response.getBody());
    }

}
