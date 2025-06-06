package dev.fernando.user_authentication_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<MessageHandler> handleUserNotFound(UserNotFound ex) {

        MessageHandler messageException = new MessageHandler(HttpStatus.NOT_FOUND, ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageException);
    }

    @ExceptionHandler(InvalidUserCredentials.class)
    public ResponseEntity<MessageHandler> handleInvalidUserCredentials(InvalidUserCredentials ex) {

        MessageHandler messageException = new MessageHandler(HttpStatus.UNAUTHORIZED, ex.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(messageException);
    }

    @ExceptionHandler(UserAlreadyExist.class)
    public ResponseEntity<MessageHandler> handleUserAlreadyExist(UserAlreadyExist ex) {

        MessageHandler messageException = new MessageHandler(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(messageException);
    }

    @ExceptionHandler(ChangePasswordIncorrect.class)
    public ResponseEntity<MessageHandler> handleChangePasswordIncorrect(ChangePasswordIncorrect ex) {

        MessageHandler messageException = new MessageHandler(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(messageException);
    }

}
