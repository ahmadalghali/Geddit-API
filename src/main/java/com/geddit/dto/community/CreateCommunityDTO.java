package com.geddit.dto.community;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateCommunityDTO(
        @NotNull
        @NotBlank
        @Size(min = 3, max = 21, message = "Community name must be between 3-21 characters")
        String name,
        @NotNull
        @NotBlank
        @Size(max = 200, message = "Community description cannot be more than 200 characters")
        String description) {}


