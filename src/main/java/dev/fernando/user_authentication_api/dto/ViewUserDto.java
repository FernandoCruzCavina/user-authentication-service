package dev.fernando.user_authentication_api.dto;

public record ViewUserDto(
        Long id,
        String username,
        String email,
        String phone) {
}
