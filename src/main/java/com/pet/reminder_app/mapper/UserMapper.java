package com.pet.reminder_app.mapper;

import com.pet.reminder_app.database.model.User;
import com.pet.reminder_app.dto.UserReadDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    User mapFromUserToUserReadDTO(UserReadDTO userReadDTO);

    UserReadDTO mapFromUserReadDTOToUser(User user);

}
