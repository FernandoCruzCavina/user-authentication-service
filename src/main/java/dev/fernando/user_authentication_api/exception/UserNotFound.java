package dev.fernando.user_authentication_api.exception;

public class UserNotFound extends RuntimeException {

    public UserNotFound() {
        super("Usuário não encontrado");
    }

    public UserNotFound(String message) {
        super(message);
    }
}
