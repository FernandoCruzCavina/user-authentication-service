package dev.fernando.user_authentication_api.exception;

public class InvalidUserCredentialsException extends RuntimeException{

    public InvalidUserCredentialsException(){
        super("email ou a senha está incorreta");
    }
}
