package com.geddit.service;

import com.geddit.converter.UserToDTOConverter;
import com.geddit.dto.UserDTO;
import com.geddit.dto.UserRegisterRequestDTO;
import com.geddit.dto.UserSignInRequestDTO;
import com.geddit.persistence.entity.AppUser;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
        private final UsersService usersService;

        public AuthService(UsersService usersService) {
        this.usersService = usersService;
        }

    public UserDTO register(UserRegisterRequestDTO userRegisterRequestDTO) {
        Optional<AppUser> userOptional =
                usersService.getUserOptionalByUsername(userRegisterRequestDTO.username());

        if (userOptional.isPresent()) throw new IllegalArgumentException("Username already exists");
        AppUser savedUser = usersService.createUser(userRegisterRequestDTO);
        return UserToDTOConverter.toDTO(savedUser);
    }

    public UserDTO signIn(UserSignInRequestDTO userSignInRequestDTO) {
        String userSignInUsername = userSignInRequestDTO.username();
        String userSignInPassword = userSignInRequestDTO.password();
        
        AppUser user = usersService.getUserByUsername(userSignInUsername);
        if (userSignInPassword.equals(user.getPassword())) {
            return UserToDTOConverter.toDTO(user);
        }
        throw new IllegalArgumentException("Password is incorrect");
    }

}
