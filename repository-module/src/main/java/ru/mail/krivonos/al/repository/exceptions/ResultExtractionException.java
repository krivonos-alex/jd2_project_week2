package ru.mail.krivonos.al.repository.exceptions;

public class ResultExtractionException extends RuntimeException {

    public ResultExtractionException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
