package dev.fernando.user_authentication_api.exception;

public class InvalidUserCredentials extends RuntimeException{

    public InvalidUserCredentials(){
        super("email ou a senha está incorreta");
    }
}
