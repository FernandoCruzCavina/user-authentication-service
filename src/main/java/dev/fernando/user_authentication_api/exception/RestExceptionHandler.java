package dev.fernando.user_authentication_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<MessageHandler> handleUserNotFound(UserNotFoundException ex) {

        MessageHandler messageException = new MessageHandler(HttpStatus.NOT_FOUND, ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageException);
    }

    @ExceptionHandler(InvalidUserCredentialsException.class)
    public ResponseEntity<MessageHandler> handleInvalidUserCredentials(InvalidUserCredentialsException ex) {

        MessageHandler messageException = new MessageHandler(HttpStatus.UNAUTHORIZED, ex.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(messageException);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<MessageHandler> handleUserAlreadyExist(UserAlreadyExistException ex) {

        MessageHandler messageException = new MessageHandler(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(messageException);
    }

    @ExceptionHandler(ChangePasswordIncorrectException.class)
    public ResponseEntity<MessageHandler> handleChangePasswordIncorrect(ChangePasswordIncorrectException ex) {

        MessageHandler messageException = new MessageHandler(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(messageException);
    }

}
