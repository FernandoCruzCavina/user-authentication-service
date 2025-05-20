package dev.fernando.user_authentication_api.exception;

import dev.fernando.user_authentication_api.model.User;

public class UserAlreadyExist extends RuntimeException{

    public UserAlreadyExist(User user) {
        super("User with " + user.getEmail() + " already exist");
    }
}
