package dev.fernando.user_authentication_api.dto;

/**
 * Data Transfer Object for updating user information.
 * Contains fields for username, old and new passwords, and phone number.
 *
 * @param username    the new username
 * @param oldPassword the current password
 * @param newPassword the new password
 * @param phone       the new phone number
 * 
 * @author Fernando Cruz Cavina
 * @since 1.0.0
 */
public record UpdateUserDto(
        String username,
        String oldPassword,
        String newPassword,
        String phone
) {
}
