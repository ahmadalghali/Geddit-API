package com.geddit.converter;

import com.geddit.dto.UserDTO;
import com.geddit.persistence.entity.AppUser;
import java.util.List;
import java.util.stream.Collectors;

public class UserToDTOConverter {
    public static UserDTO toDTO(AppUser user) {
        return new UserDTO(user.getId(), user.getUsername());
    }
    public static List<UserDTO> toDTOList(List<AppUser> users) {
        return users.stream().map(UserToDTOConverter::toDTO).collect(Collectors.toList());
    }
}
