package dev.fernando.user_authentication_api.dto;

public record CreateUserDto(
    String username,
    String email,
    String password,
    String phone
){}
