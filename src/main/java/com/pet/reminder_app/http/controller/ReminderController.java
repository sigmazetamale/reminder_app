package com.pet.reminder_app.http.controller;

import com.pet.reminder_app.dto.ReminderCreateEditDTO;
import com.pet.reminder_app.dto.ReminderReadDTO;
import com.pet.reminder_app.service.ReminderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ReminderController {

    private final ReminderService reminderService;

    @GetMapping("/list")
    public List<ReminderReadDTO> findAllReminders() {
        return reminderService.findAllReminders();
    }


    @PostMapping(value = "/reminder/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ReminderReadDTO create(@RequestBody @Validated ReminderCreateEditDTO reminderCreateEditDTO) {
        return reminderService.create(reminderCreateEditDTO);
    }

    @DeleteMapping("/reminder/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        if (!reminderService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/reminder/update/{id}")
    public ReminderReadDTO update(@PathVariable("id") Long id, @RequestBody @Validated ReminderCreateEditDTO reminderCreateEditDTO) {
        return reminderService.update(id, reminderCreateEditDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }


}
