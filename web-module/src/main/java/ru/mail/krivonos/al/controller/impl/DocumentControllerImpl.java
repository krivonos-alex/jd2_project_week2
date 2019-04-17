package ru.mail.krivonos.al.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.mail.krivonos.al.controller.DocumentController;
import ru.mail.krivonos.al.controller.validator.DocumentDTOValidator;
import ru.mail.krivonos.al.service.DocumentService;
import ru.mail.krivonos.al.service.model.DocumentDTO;

@Controller("documentController")
public class DocumentControllerImpl implements DocumentController {

    private final DocumentService documentService;
    private final DocumentDTOValidator documentDTOValidator;

    @Autowired
    public DocumentControllerImpl(DocumentService documentService, DocumentDTOValidator documentDTOValidator) {
        this.documentService = documentService;
        this.documentDTOValidator = documentDTOValidator;
    }

    @Override
    public DocumentDTO add(DocumentDTO documentDTO) {
        documentDTOValidator.validateArgumentDocumentDTO(documentDTO);
        DocumentDTO returningDTO = documentService.add(documentDTO);
        documentDTOValidator.validateReturningDocumentDTO(returningDTO);
        return returningDTO;
    }

    @Override
    public DocumentDTO getDocumentById(Long id) {
        documentDTOValidator.validateID(id);
        DocumentDTO returningDTO = documentService.getDocumentById(id);
        documentDTOValidator.validateReturningDocumentDTO(returningDTO);
        return returningDTO;
    }

    @Override
    public void delete(Long id) {
        documentDTOValidator.validateID(id);
        documentService.delete(id);
    }
}
