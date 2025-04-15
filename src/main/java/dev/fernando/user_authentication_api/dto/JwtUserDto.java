package dev.fernando.user_authentication_api.dto;

import dev.fernando.user_authentication_api.constants.UserRole;

public record JwtUserDto(
        long id,
        String username,
        String email,
        String phone,
        UserRole userRole
)
{}
