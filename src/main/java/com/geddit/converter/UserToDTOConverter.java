package com.geddit.converter;

import com.geddit.dto.UserDTO;
import com.geddit.persistence.entity.AppUser;

import java.util.List;
import java.util.stream.Collectors;

public class UserToDTOConverter {

    public static UserDTO toDTO(AppUser user) {

//        var followers =
        var email = user.getUsername();
        var username = email.substring(0, email.indexOf("@"));
        return new UserDTO(
                user.getId(),
                email,
                username,
                user.getProfileImageUrl(),
                user.getFollowers().size(),
                user.getFollowing().size());
    }

    public static List<UserDTO> toDTOList(List<AppUser> users) {
        return users.stream().map(UserToDTOConverter::toDTO).collect(Collectors.toList());
    }
}
