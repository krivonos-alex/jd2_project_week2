package ru.mail.krivonos.al.repository.exceptions;

public class DatabaseInsertQueryException extends RuntimeException {

    public DatabaseInsertQueryException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
