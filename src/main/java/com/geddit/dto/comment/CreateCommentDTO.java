package com.geddit.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateCommentDTO (
        @NotBlank
        @NotNull
        @Size(max = 10_000, message = "Comment cannot be more than 10,000 characters")
        String text) {}
