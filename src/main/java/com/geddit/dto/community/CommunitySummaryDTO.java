package com.geddit.dto.community;

import com.geddit.dto.UserDTO;

import java.time.Instant;

public record CommunitySummaryDTO(
        String name, String description, String imageUrl, long postCount, Integer memberCount, boolean isMember, Instant createdDate, UserDTO createdBy) {}
