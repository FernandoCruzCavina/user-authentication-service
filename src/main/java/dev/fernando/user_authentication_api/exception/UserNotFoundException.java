package dev.fernando.user_authentication_api.exception;

/**
 * Exception thrown when a user is not found in the system.
 * This exception is used to indicate that the requested user does not exist.
 * 
 * @author Fernando Cruz Cavina
 * @since 1.0.0
 */
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("Usuário não encontrado");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
