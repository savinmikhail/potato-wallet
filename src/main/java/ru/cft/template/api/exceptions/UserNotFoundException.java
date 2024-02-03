package ru.cft.template.api.exceptions;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException() {
//        super("{api.wallet.user.by-id.404}");
        super("User not found");
    }
}
