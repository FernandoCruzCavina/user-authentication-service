package dev.fernando.user_authentication_api.dto;


import java.util.Date;

import dev.fernando.user_authentication_api.enums.UserRole;

/**
 * Data Transfer Object for viewing user details.
 * Contains user information to be sent in API responses.
 * 
 * @param id the user ID
 * @param username the user's name
 * @param email the user's email
 * @param phone the user's phone number
 * @param birthdayDate the user's birthday date
 * @param cpf the user's CPF (Brazilian ID)
 * @param userRole the user's role
 * 
 * @author Fernando Cruz Cavina
 * @since 1.0.0
 */
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
