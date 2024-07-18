package com.pet.reminder_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserReadDTO{

    private Long id;

    private String email;

}