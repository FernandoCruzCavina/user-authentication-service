package dev.fernando.user_authentication_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<MessageException> handleUserNotFound(UserNotFound ex) {

        MessageException messageException = new MessageException(HttpStatus.NOT_FOUND, ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageException);
    }

    @ExceptionHandler(InvalidUserCredentials.class)
    public ResponseEntity<MessageException> handleInvalidUserCredentials(InvalidUserCredentials ex) {

        MessageException messageException = new MessageException(HttpStatus.UNAUTHORIZED, ex.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(messageException);
    }

    @ExceptionHandler(UserAlreadyExist.class)
    public ResponseEntity<MessageException> handleUserAlreadyExist(UserAlreadyExist ex) {

        MessageException messageException = new MessageException(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(messageException);
    }

    @ExceptionHandler(ChangePasswordIncorrect.class)
    public ResponseEntity<MessageException> handleChangePasswordIncorrect(ChangePasswordIncorrect ex) {

        MessageException messageException = new MessageException(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(messageException);
    }

}
