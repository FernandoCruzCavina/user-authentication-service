package dev.fernando.user_authentication_api.exception;
/**
 * Exception thrown when the current password provided for changing a user's password is incorrect.
 * This exception is used to indicate that the user has entered an incorrect current password
 * while attempting to change their password.
 * 
 * @author Fernando Cruz Cavina
 * @since 1.0.0
 */
public class ChangePasswordIncorrectException extends RuntimeException{

    public ChangePasswordIncorrectException(){
        super("A senha atual está incorreta");
    }
}