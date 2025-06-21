package dev.fernando.user_authentication_api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Class representing a message handler for exceptions.
 * It extends ResponseEntityExceptionHandler to handle exceptions in a Spring application.
 * It contains the HTTP status and message to be returned in the response.
 * 
 * @author Fernando Cruz Cavina
 * @since 1.0.0
 */
@AllArgsConstructor
@Getter
@Setter
public class MessageHandler extends ResponseEntityExceptionHandler {

    private HttpStatus status;
    private String message;
}
