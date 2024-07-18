package com.pet.reminder_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SortedRemindersRq {


    private boolean sortedByName;

    private boolean sortedByDate;

    private boolean sortedByTime;

    private Long userId;


}
