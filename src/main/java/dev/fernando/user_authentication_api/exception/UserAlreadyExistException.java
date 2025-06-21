package dev.fernando.user_authentication_api.exception;

import dev.fernando.user_authentication_api.model.User;

/**
 * Exception thrown when a user with the specified email already exists.
 * This exception is used to indicate that an attempt to create a new user
 * with an email that is already registered in the system has been made.
 * 
 * @author Fernando Cruz Cavina
 * @since 1.0.0
 */
public class UserAlreadyExistException extends RuntimeException{

    public UserAlreadyExistException(User user) {
        super("Usuário com email " + user.getEmail() + " já existe");
    }
}
