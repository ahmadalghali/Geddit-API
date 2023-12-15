package com.geddit.dto;

public record UserDTO (String id, String email, String username, String profileImageUrl, Integer followerCount, Integer followingCount ) {};
