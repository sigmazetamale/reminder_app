package com.pet.reminder_app.http.controller;

import com.pet.reminder_app.dto.*;
import com.pet.reminder_app.service.ReminderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ReminderController {

    private final ReminderService reminderService;

    @PostMapping(value = "/list", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RemindersRs> findAllReminders(@RequestBody @Validated RemindersListRq remindersListRq) {
        return new ResponseEntity<>(reminderService
                .findAllReminders(remindersListRq.getUserId(), remindersListRq.getPageNumber(), remindersListRq.getPageSize()), HttpStatus.OK);
    }

    @PostMapping(value = "/sort", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReminderReadDTO>> findAllSortedReminders(@RequestBody SortedRemindersRq sortedRemindersRq) {
        return new ResponseEntity<>(reminderService.findAllSortedReminders(sortedRemindersRq), HttpStatus.OK);

    }

    @PostMapping(value = "/filtr", consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<List<ReminderReadDTO>> findAllFilteredReminders(@RequestBody @Validated ReminderFilter reminderFilter) {
        return new ResponseEntity<>(reminderService.findAllReminders(reminderFilter), HttpStatus.OK);
    }



    @PostMapping(value = "/reminder/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReminderReadDTO> create(@RequestBody @Validated ReminderCreateEditDTO reminderCreateEditDTO) {
        return new ResponseEntity<>(reminderService.create(reminderCreateEditDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/reminder/delete/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
        if (!reminderService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/reminder/update/{id}")
    public ResponseEntity<ReminderReadDTO> update(@PathVariable("id") Long id, @RequestBody @Validated ReminderCreateEditDTO reminderCreateEditDTO) {
        return new ResponseEntity<>(reminderService.update(id, reminderCreateEditDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)), HttpStatus.OK);
    }


}
