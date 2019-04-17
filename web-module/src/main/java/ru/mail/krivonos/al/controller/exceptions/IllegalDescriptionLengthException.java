package ru.mail.krivonos.al.controller.exceptions;

public class IllegalDescriptionLengthException extends RuntimeException {

    public IllegalDescriptionLengthException(String s) {
        super(s);
    }
}
