package ru.mail.krivonos.al.controller.validator;

import ru.mail.krivonos.al.service.model.DocumentDTO;

public interface DocumentDTOValidator {

    void validateID(Long id);

    void validateReturningDocumentDTO(DocumentDTO returningDocumentDTO);

    void validateArgumentDocumentDTO(DocumentDTO documentDTO);
}
