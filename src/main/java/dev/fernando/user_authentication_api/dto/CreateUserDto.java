package dev.fernando.user_authentication_api.dto;

import dev.fernando.user_authentication_api.constants.UserRole;

public record CreateUserDto(
    String username,
    String email,
    String password,
    String phone,
    UserRole user_role
){}
