package dev.fernando.user_authentication_api.dto;

import dev.fernando.user_authentication_api.enums.UserRole;

import java.util.Date;

public record CreateUserDto(
    String username,
    String email,
    String password,
    String phone,
    String cpf,
    Date birthday_date,
    UserRole user_role
){}
