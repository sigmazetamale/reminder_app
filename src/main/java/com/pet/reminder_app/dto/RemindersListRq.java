package com.pet.reminder_app.dto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RemindersListRq {

    private Long userId;

    @Min(value = 1, message = "Parameter 'pageNumber' must be greater than zero")
    private int pageNumber;

    @Min(value = 1, message = "Parameter 'pageNumber' must be greater than zero")
    private int pageSize;
}
