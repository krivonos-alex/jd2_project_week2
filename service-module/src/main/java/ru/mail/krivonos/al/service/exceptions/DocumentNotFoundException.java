package ru.mail.krivonos.al.service.exceptions;

public class DocumentNotFoundException extends RuntimeException {

    public DocumentNotFoundException(String s) {
        super(s);
    }
}
