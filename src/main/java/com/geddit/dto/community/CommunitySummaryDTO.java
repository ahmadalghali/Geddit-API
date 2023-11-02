package com.geddit.dto.community;

import com.geddit.dto.UserDTO;

import java.time.LocalDateTime;

public record CommunitySummaryDTO(
        String name, String description, String imageUrl, long postCount, Integer memberCount, LocalDateTime createdDate, UserDTO createdBy) {}
