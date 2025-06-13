package dev.fernando.user_authentication_api.exception;

import dev.fernando.user_authentication_api.model.User;

public class UserAlreadyExistException extends RuntimeException{

    public UserAlreadyExistException(User user) {
        super("Usuário com email " + user.getEmail() + " já existe");
    }
}
