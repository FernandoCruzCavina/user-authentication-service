package dev.fernando.user_authentication_api.exception;
/**
 * Exception thrown when the user credentials (email or password) are invalid.
 * This exception is used to indicate that the user has entered incorrect credentials
 * while attempting to log in.
 * 
 * @author Fernando Cruz Cavina
 * @since 1.0.0
 */
public class InvalidUserCredentialsException extends RuntimeException{

    public InvalidUserCredentialsException(){
        super("email ou a senha está incorreta");
    }
}
