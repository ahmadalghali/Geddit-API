package com.geddit.dto.auth;


public record UserSignInResponseDTO(
        String accessToken,
        String refreshToken) {
}
