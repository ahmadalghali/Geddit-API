package com.geddit.controller;

import com.geddit.dto.auth.UserRegisterRequestDTO;
import com.geddit.dto.auth.UserSignInRequestDTO;
import com.geddit.dto.auth.UserSignInResponseDTO;
import com.geddit.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserSignInResponseDTO> register(
            @RequestBody UserRegisterRequestDTO request
    ) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/temporary-demo-sign-in")
    public ResponseEntity<UserSignInResponseDTO> register() {
        return ResponseEntity.ok(authService.temporaryDemoRegistration());
    }


    @PostMapping("/sign-in")
    public ResponseEntity<UserSignInResponseDTO> signIn(
            @RequestBody UserSignInRequestDTO request
    ) {
        return ResponseEntity.ok(authService.signIn(request));
    }


//    @PostMapping("/refresh-token")
//    public void refreshToken(
//            HttpServletRequest request,
//            HttpServletResponse response
//    )  {
//        authService.refreshToken(request, response);
//    }

//    @PostMapping("/register")
//    @ResponseStatus(HttpStatus.CREATED)
//    public UserDTO register(@RequestBody UserRegisterRequestDTO userRegisterRequestDTO) {
//        return authService.register(userRegisterRequestDTO);
//    }
//
//    @PostMapping("/sign-in")
//    @ResponseStatus(HttpStatus.OK)
//    public UserDTO signIn(@RequestBody UserSignInRequestDTO userSignInRequestDTO) {
//        return authService.signIn(userSignInRequestDTO);
//    }
}
