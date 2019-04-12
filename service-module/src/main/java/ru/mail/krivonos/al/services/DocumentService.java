package ru.mail.krivonos.al.services;

import ru.mail.krivonos.al.services.model.DocumentDTO;

public interface DocumentService {

    DocumentDTO add(DocumentDTO documentDTO);

    DocumentDTO getDocumentById(Long id);

    void delete(Long id);
}
