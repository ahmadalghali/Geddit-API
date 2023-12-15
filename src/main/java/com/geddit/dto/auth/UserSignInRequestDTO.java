package com.geddit.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserSignInRequestDTO(
        @NotBlank
        @NotNull
        String email,
        @NotBlank
        @NotNull
        String password
) {}
