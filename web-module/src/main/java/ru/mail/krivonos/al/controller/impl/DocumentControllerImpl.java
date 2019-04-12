package ru.mail.krivonos.al.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import ru.mail.krivonos.al.controller.DocumentController;
import ru.mail.krivonos.al.controller.exceptions.*;
import ru.mail.krivonos.al.services.DocumentService;
import ru.mail.krivonos.al.services.model.DocumentDTO;

@Controller("documentController")
public class DocumentControllerImpl implements DocumentController {

    private final DocumentService documentService;

    private static final String UNIQUE_NUMBER_VALIDATION_REGEX = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-" +
            "[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";

    private static final int DESCRIPTION_LENGTH_LIMIT = 100;

    @Autowired
    public DocumentControllerImpl(
            @Qualifier("documentService") DocumentService documentService
    ) {
        this.documentService = documentService;
    }

    @Override
    public DocumentDTO add(DocumentDTO documentDTO) {
        validateArgumentDocumentDTO(documentDTO);
        DocumentDTO returningDTO = documentService.add(documentDTO);
        validateReturningDocumentDTO(returningDTO);
        return returningDTO;
    }

    @Override
    public DocumentDTO getDocumentById(Long id) {
        validateID(id);
        DocumentDTO returningDTO = documentService.getDocumentById(id);
        validateReturningDocumentDTO(returningDTO);
        return returningDTO;
    }

    @Override
    public void delete(Long id) {
        validateID(id);
        documentService.delete(id);
    }

    private void validateID(Long id) {
        if (id == null) {
            throw new NullIDArgumentException("ID argument is null.");
        }
    }

    private void validateReturningDocumentDTO(DocumentDTO returningDocumentDTO) {
        if (returningDocumentDTO == null) {
            throw new NullReturningDocumentDTOException("Service returned null value.");
        }
        if (returningDocumentDTO.getId() == null) {
            throw new NullDocumentDTOIDException("Returning DocumentDTO should contain ID.");
        }
    }

    private void validateArgumentDocumentDTO(DocumentDTO documentDTO) {
        if (documentDTO == null) {
            throw new NullArgumentDocumentDTOException("Argument is not presented.");
        }
        if (!documentDTO.getUniqueNumber().matches(UNIQUE_NUMBER_VALIDATION_REGEX)) {
            throw new IllegalUniqueNumberFormatException("Unique number: \"" + documentDTO.getUniqueNumber() +
                    "\" doesn't have suitable format.");
        }
        if (documentDTO.getDescription() == null) {
            throw new NullDocumentDTODescriptionException("Description should be presented.");
        }
        if (documentDTO.getDescription().length() > DESCRIPTION_LENGTH_LIMIT) {
            throw new IllegalDescriptionLengthException("Description should be not longer than 100 symbols. Current " +
                    "description contains " + documentDTO.getDescription().length() + " symbols.");
        }
    }
}
