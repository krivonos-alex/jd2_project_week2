package ru.mail.krivonos.al.repository;

import ru.mail.krivonos.al.repository.model.Document;

public interface DocumentRepository {

    Document add(Document document);

    Document findDocumentByID(Long id);

    void delete(Long id);
}
