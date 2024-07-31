package com.pet.reminder_app.http.controller;

import com.pet.reminder_app.dto.*;
import com.pet.reminder_app.service.ReminderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ReminderController {

    private final ReminderService reminderService;

    @PostMapping(value = "/list")
    public ResponseEntity<RemindersRs> findAllReminders(@RequestBody @Validated RemindersListRq remindersListRq, @AuthenticationPrincipal OAuth2User authentication) {
        return new ResponseEntity<>(reminderService
                .findAllReminders(authentication, remindersListRq.getPageNumber(), remindersListRq.getPageSize()), HttpStatus.OK);
    }

    @PostMapping(value = "/sort", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReminderReadDTO>> findAllSortedReminders(@RequestBody SortedRemindersRq sortedRemindersRq, @AuthenticationPrincipal OAuth2User authentication) {
        return new ResponseEntity<>(reminderService.findAllSortedReminders(authentication, sortedRemindersRq), HttpStatus.OK);

    }

    @PostMapping(value = "/filtr", consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<List<ReminderReadDTO>> findAllFilteredReminders(@RequestBody @Validated ReminderFilter reminderFilter, @AuthenticationPrincipal OAuth2User authentication) {
        return new ResponseEntity<>(reminderService.findAllReminders(authentication, reminderFilter), HttpStatus.OK);
    }


    @PostMapping(value = "/reminder/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReminderReadDTO> create(@RequestBody @Validated ReminderCreateEditDTO reminderCreateEditDTO, @AuthenticationPrincipal OAuth2User authentication) {
        return new ResponseEntity<>(reminderService.create(authentication, reminderCreateEditDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/reminder/delete/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id, @AuthenticationPrincipal OAuth2User authentication) {
        if (!reminderService.delete(authentication, id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/reminder/update/{id}")
    public ResponseEntity<ReminderReadDTO> update(@PathVariable("id") Long id, @RequestBody @Validated ReminderCreateEditDTO reminderCreateEditDTO, @AuthenticationPrincipal OAuth2User authentication) {
        return new ResponseEntity<>(reminderService.update(authentication, id, reminderCreateEditDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)), HttpStatus.OK);
    }


}
