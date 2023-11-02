package com.geddit.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserSignInRequestDTO(
        @NotBlank
        @NotNull
        String username,
        @NotBlank
        @NotNull
        String password
) {}
