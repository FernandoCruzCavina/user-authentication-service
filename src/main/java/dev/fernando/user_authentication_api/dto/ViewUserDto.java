package dev.fernando.user_authentication_api.dto;

public record ViewUserDto (
    long id,
    String username,
    String email,
    String phone
){}
