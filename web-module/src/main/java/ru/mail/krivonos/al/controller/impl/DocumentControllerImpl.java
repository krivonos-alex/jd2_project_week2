package ru.mail.krivonos.al.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import ru.mail.krivonos.al.controller.DocumentController;
import ru.mail.krivonos.al.controller.constants.ControllerErrorMessageConstants;
import ru.mail.krivonos.al.controller.exceptions.IllegalDescriptionLengthException;
import ru.mail.krivonos.al.controller.exceptions.IllegalUniqueNumberFormatException;
import ru.mail.krivonos.al.controller.exceptions.NullArgumentDocumentDTOException;
import ru.mail.krivonos.al.controller.exceptions.NullDocumentDTODescriptionException;
import ru.mail.krivonos.al.controller.exceptions.NullDocumentDTOIDException;
import ru.mail.krivonos.al.controller.exceptions.NullIDArgumentException;
import ru.mail.krivonos.al.controller.exceptions.NullReturningDocumentDTOException;
import ru.mail.krivonos.al.service.DocumentService;
import ru.mail.krivonos.al.service.model.DocumentDTO;

@Controller("documentController")
public class DocumentControllerImpl implements DocumentController {

    private static final String UNIQUE_NUMBER_VALIDATION_REGEX = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-" +
            "[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";
    private static final int DESCRIPTION_LENGTH_LIMIT = 100;
    private final DocumentService documentService;

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
            throw new NullIDArgumentException(ControllerErrorMessageConstants.NULL_ID_ERROR_MESSAGE);
        }
    }

    private void validateReturningDocumentDTO(DocumentDTO returningDocumentDTO) {
        if (returningDocumentDTO == null) {
            throw new NullReturningDocumentDTOException(ControllerErrorMessageConstants.NULL_RETURNING_ERROR_MESSAGE);
        }
        if (returningDocumentDTO.getId() == null) {
            throw new NullDocumentDTOIDException(ControllerErrorMessageConstants.NULL_DTO_ID_ERROR_MESSAGE);
        }
    }

    private void validateArgumentDocumentDTO(DocumentDTO documentDTO) {
        if (documentDTO == null) {
            throw new NullArgumentDocumentDTOException(ControllerErrorMessageConstants.NULL_ARGUMENT_ERROR_MESSAGE);
        }
        if (!documentDTO.getUniqueNumber().matches(UNIQUE_NUMBER_VALIDATION_REGEX)) {
            throw new IllegalUniqueNumberFormatException(String.format("Unique number: %s doesn't have suitable" +
                    " format.", documentDTO.getUniqueNumber()));
        }
        if (documentDTO.getDescription() == null) {
            throw new NullDocumentDTODescriptionException(ControllerErrorMessageConstants.NULL_DESCRIPTION_ERROR_MESSAGE);
        }
        if (documentDTO.getDescription().length() > DESCRIPTION_LENGTH_LIMIT) {
            throw new IllegalDescriptionLengthException(String.format("Description should be not longer than 100 " +
                    "symbols. Current description contains %d symbols.", documentDTO.getDescription().length()));
        }
    }
}
