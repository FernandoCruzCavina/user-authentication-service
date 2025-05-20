package dev.fernando.user_authentication_api.dto;

public record UpdateUserDto (
        String username,
        String password,
        String phone
) {
}
