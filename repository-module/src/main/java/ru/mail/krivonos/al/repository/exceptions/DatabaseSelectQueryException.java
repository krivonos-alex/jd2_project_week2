package ru.mail.krivonos.al.repository.exceptions;

public class DatabaseSelectQueryException extends RuntimeException {

    public DatabaseSelectQueryException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
