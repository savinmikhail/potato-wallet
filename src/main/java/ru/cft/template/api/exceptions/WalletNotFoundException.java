package ru.cft.template.api.exceptions;

public class WalletNotFoundException extends RuntimeException{

    public WalletNotFoundException() {
        super("api.wallet.wallet.by-id.404");
    }
}