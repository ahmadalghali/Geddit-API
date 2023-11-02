package com.geddit.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRegisterRequestDTO(
        @NotBlank
        @NotNull
        String username,
        @NotBlank
        @NotNull
        @Size(min = 8, message = "Password must be at least 8 characters")
        String password
) {}
