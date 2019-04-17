package ru.mail.krivonos.al.repository.exceptions;

public class DatabaseUpdateQueryException extends RuntimeException {

    public DatabaseUpdateQueryException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
