package com.geddit.dto.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreatePostDTO (
        @NotBlank
        @Size(max = 200, message = "Post title cannot be more than 200 characters")
        String title,
        @Size(max = 10_000, message = "Post body cannot be more than 10,000 characters")
        String body) { }
