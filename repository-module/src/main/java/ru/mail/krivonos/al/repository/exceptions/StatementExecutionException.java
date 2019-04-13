package ru.mail.krivonos.al.repository.exceptions;

public class StatementExecutionException extends RuntimeException {

    public StatementExecutionException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
