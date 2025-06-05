package dev.fernando.user_authentication_api.dto;


import java.util.Date;

import dev.fernando.user_authentication_api.enums.UserRole;

public record ViewUserDto(
        Long id,
        String username,
        String email,
        String phone,
        Date birthdayDate,
        String cpf,
        UserRole userRole
) {
}
