package ru.mail.krivonos.al.controller;

import ru.mail.krivonos.al.service.model.DocumentDTO;

public interface DocumentController {

    DocumentDTO add(DocumentDTO documentDTO);

    DocumentDTO getDocumentById(Long id);

    void delete(Long id);
}
