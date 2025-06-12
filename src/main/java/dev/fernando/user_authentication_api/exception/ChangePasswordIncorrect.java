package dev.fernando.user_authentication_api.exception;

public class ChangePasswordIncorrect extends RuntimeException{

    public ChangePasswordIncorrect(){
        super("A senha atual está incorreta");
    }
}