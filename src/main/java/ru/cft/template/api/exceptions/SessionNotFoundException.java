package ru.cft.template.api.exceptions;

public class SessionNotFoundException  extends RuntimeException{
    public SessionNotFoundException() {
        super("session not found");
    }
}
