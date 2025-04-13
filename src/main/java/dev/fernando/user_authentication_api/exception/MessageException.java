package dev.fernando.user_authentication_api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@AllArgsConstructor
@Getter
@Setter
public class MessageException extends ResponseEntityExceptionHandler {

    private HttpStatus status;
    private String message;
}
