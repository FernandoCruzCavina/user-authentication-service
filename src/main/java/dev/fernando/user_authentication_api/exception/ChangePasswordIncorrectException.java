package dev.fernando.user_authentication_api.exception;

public class ChangePasswordIncorrectException extends RuntimeException{

    public ChangePasswordIncorrectException(){
        super("A senha atual está incorreta");
    }
}