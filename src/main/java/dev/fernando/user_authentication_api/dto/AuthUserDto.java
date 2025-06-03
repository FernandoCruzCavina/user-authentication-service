package dev.fernando.user_authentication_api.dto;

import dev.fernando.user_authentication_api.enums.UserRole;

public record AuthUserDto(
    String email,
    String password,
    UserRole userRole
) {}
