package ru.mail.krivonos.al.service;

import ru.mail.krivonos.al.service.model.DocumentDTO;

public interface DocumentService {

    DocumentDTO add(DocumentDTO documentDTO);

    DocumentDTO getDocumentById(Long id);

    void delete(Long id);
}
