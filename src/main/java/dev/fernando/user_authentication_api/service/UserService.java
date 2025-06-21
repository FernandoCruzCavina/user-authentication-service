package dev.fernando.user_authentication_api.service;

import dev.fernando.user_authentication_api.dto.CreateUserDto;
import dev.fernando.user_authentication_api.dto.UpdateUserDto;
import dev.fernando.user_authentication_api.dto.ViewUserDto;
import dev.fernando.user_authentication_api.exception.*;
/**
 * Service interface for managing user operations.
 * This interface defines methods for finding, creating, updating, and deleting users.
 * It also includes methods for handling user-related exceptions.
 * 
 * @author Fernando Cruz Cavina
 * @since 1.0.0
 */
public interface UserService {

    /**
     * Finds a user by their ID.
     *
     * @param id the ID of the user to find
     * @return the user details which includes ID, username, email, role, phone, cpf, birthdate and creation date
     * @throws UserNotFoundException if no user with the given ID exists
     */
    public ViewUserDto findUserById(Long id);

    /**
     * Finds a user by their email.
     *
     * @param email the email of the user to find
     * @return the user details which includes ID, username, email, role, phone, cpf, birthdate and creation date
     * @throws UserNotFoundException if no user with the given email exists
     */
    public ViewUserDto findUserByEmail(String email);

    /**
     * Creates a new user with default USER role about {@link dev.fernando.user_authentication_api.enums.UserRole},
     * in other words, a client of user service.
     *
     * @param createUserDto the details of the user to create
     * @return the created user details which includes ID, username, email, role, phone, cpf, birthdate and creation date
     * @throws UserAlreadyExistException if a user with the same email already exists
     */
    public ViewUserDto createUserWithDefaultRole(CreateUserDto createUserDto);

    /**
     * updates an existing user as long as the current password is confirmed during the update.
     *
     * @param id the ID of the user to update
     * @param updateUserDto the new details for the user
     * @return the updated user details which includes ID, username, email, role, phone, cpf, birthdate and creation date
     * @throws UserNotFoundException if no user with the given ID exists
     * @throws ChangePasswordIncorrectException if the current password provided does not match the user's existing password
     */
    public ViewUserDto updateUser(Long id, UpdateUserDto updateUserDto);

    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user to delete
     * @return the deleted user details which includes ID, username, email, role, phone, cpf, birthdate and creation date
     * @throws UserNotFoundException if no user with the given ID exists
     */
    public ViewUserDto deleteUserById(Long id);

    /**
     * Deletes a user by their email.
     *
     * @param email the email of the user to delete
     * @return the deleted user details which includes ID, username, email, role, phone, cpf, birthdate and creation date
     * @throws UserNotFoundException if no user with the given email exists
     */
    public ViewUserDto deleteUserByEmail(String email);
}
