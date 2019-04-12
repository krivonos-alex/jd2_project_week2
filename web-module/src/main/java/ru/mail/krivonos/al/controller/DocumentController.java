package ru.mail.krivonos.al.controller;

import ru.mail.krivonos.al.services.model.DocumentDTO;

public interface DocumentController {

    DocumentDTO add(DocumentDTO documentDTO);

    DocumentDTO getDocumentById(Long id);

    void delete(Long id);
}
