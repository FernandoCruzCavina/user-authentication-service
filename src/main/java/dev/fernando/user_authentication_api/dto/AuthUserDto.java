package dev.fernando.user_authentication_api.dto;

import dev.fernando.user_authentication_api.enums.UserRole;

/**
 * Data Transfer Object for sending user authentication credentials.
 * Used for communication with authentication services.
 * 
 * @param email the user's email address
 * @param password the user's password
 * @param userRole the user's role in the system
 * 
 * @author Fernando Cruz Cavina
 * @since 1.0.0
 */
public record AuthUserDto(
    String email,
    String password,
    UserRole userRole
) {}
