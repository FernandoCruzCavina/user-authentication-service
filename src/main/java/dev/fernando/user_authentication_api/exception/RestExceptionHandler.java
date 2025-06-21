package dev.fernando.user_authentication_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler for the user request the API.
 * This class handles various exceptions that may occur during the execution of the application
 * and returns appropriate HTTP responses with error messages.
 * 
 * @author Fernando Cruz Cavina
 * @since 1.0.0
 */
@ControllerAdvice
public class RestExceptionHandler {

    /**
     * Handles UserNotFoundException and returns a 404 NOT FOUND response.
     *
     * @param ex the thrown UserNotFoundException
     * @return a ResponseEntity containing a MessageHandler with NOT_FOUND status and error message
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<MessageHandler> handleUserNotFound(UserNotFoundException ex) {
        MessageHandler messageException = new MessageHandler(HttpStatus.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageException);
    }

    /**
     * Handles InvalidUserCredentialsException and returns a 401 UNAUTHORIZED response.
     *
     * @param ex the thrown InvalidUserCredentialsException
     * @return a ResponseEntity containing a MessageHandler with UNAUTHORIZED status and error message
     */
    @ExceptionHandler(InvalidUserCredentialsException.class)
    public ResponseEntity<MessageHandler> handleInvalidUserCredentials(InvalidUserCredentialsException ex) {
        MessageHandler messageException = new MessageHandler(HttpStatus.UNAUTHORIZED, ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(messageException);
    }

    /**
     * Handles UserAlreadyExistException and returns a 422 UNPROCESSABLE ENTITY response.
     *
     * @param ex the thrown UserAlreadyExistException
     * @return a ResponseEntity containing a MessageHandler with UNPROCESSABLE_ENTITY status and error message
     */
    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<MessageHandler> handleUserAlreadyExist(UserAlreadyExistException ex) {
        MessageHandler messageException = new MessageHandler(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(messageException);
    }

    /**
     * Handles ChangePasswordIncorrectException and returns a 422 UNPROCESSABLE ENTITY response.
     *
     * @param ex the thrown ChangePasswordIncorrectException
     * @return a ResponseEntity containing a MessageHandler with UNPROCESSABLE_ENTITY status and error message
     */
    @ExceptionHandler(ChangePasswordIncorrectException.class)
    public ResponseEntity<MessageHandler> handleChangePasswordIncorrect(ChangePasswordIncorrectException ex) {
        MessageHandler messageException = new MessageHandler(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(messageException);
    }

}
