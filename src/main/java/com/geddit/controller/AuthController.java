package com.geddit.controller;

import com.geddit.dto.UserDTO;
import com.geddit.dto.UserRegisterRequestDTO;
import com.geddit.dto.UserSignInRequestDTO;
import com.geddit.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO register(@RequestBody UserRegisterRequestDTO userRegisterRequestDTO) {
        return authService.register(userRegisterRequestDTO);
    }

    @PostMapping("/sign-in")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO signIn(@RequestBody UserSignInRequestDTO userSignInRequestDTO) {
        return authService.signIn(userSignInRequestDTO);
    }
}
