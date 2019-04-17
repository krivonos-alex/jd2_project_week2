package ru.mail.krivonos.al.controller.validator.impl;

import org.springframework.stereotype.Component;
import ru.mail.krivonos.al.controller.constants.ControllerErrorMessageConstants;
import ru.mail.krivonos.al.controller.exceptions.IllegalDescriptionLengthException;
import ru.mail.krivonos.al.controller.exceptions.IllegalUniqueNumberFormatException;
import ru.mail.krivonos.al.controller.exceptions.IllegalDocumentDTOStateException;
import ru.mail.krivonos.al.controller.exceptions.IllegalDocumentDTODescriptionException;
import ru.mail.krivonos.al.controller.exceptions.IllegalDocumentDTOIDException;
import ru.mail.krivonos.al.controller.exceptions.IllegalIDException;
import ru.mail.krivonos.al.controller.exceptions.IllegalReturningDocumentDTOException;
import ru.mail.krivonos.al.controller.validator.DocumentDTOValidator;
import ru.mail.krivonos.al.service.model.DocumentDTO;

@Component("documentDTOValidator")
public class DocumentDTOValidatorImpl implements DocumentDTOValidator {

    private static final String UNIQUE_NUMBER_VALIDATION_REGEX = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-" +
            "[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";
    private static final int DESCRIPTION_LENGTH_LIMIT = 100;

    @Override
    public void validateID(Long id) {
        if (id == null) {
            throw new IllegalIDException(ControllerErrorMessageConstants.NULL_ID_ERROR_MESSAGE);
        }
    }

    @Override
    public void validateReturningDocumentDTO(DocumentDTO returningDocumentDTO) {
        if (returningDocumentDTO == null) {
            throw new IllegalReturningDocumentDTOException(ControllerErrorMessageConstants.NULL_RETURNING_ERROR_MESSAGE);
        }
        if (returningDocumentDTO.getId() == null) {
            throw new IllegalDocumentDTOIDException(ControllerErrorMessageConstants.NULL_DTO_ID_ERROR_MESSAGE);
        }
    }

    @Override
    public void validateArgumentDocumentDTO(DocumentDTO documentDTO) {
        if (documentDTO == null) {
            throw new IllegalDocumentDTOStateException(ControllerErrorMessageConstants.NULL_ARGUMENT_ERROR_MESSAGE);
        }
        if (!documentDTO.getUniqueNumber().matches(UNIQUE_NUMBER_VALIDATION_REGEX)) {
            throw new IllegalUniqueNumberFormatException(String.format("Unique number: %s doesn't have suitable" +
                    " format.", documentDTO.getUniqueNumber()));
        }
        if (documentDTO.getDescription() == null) {
            throw new IllegalDocumentDTODescriptionException(ControllerErrorMessageConstants.NULL_DESCRIPTION_ERROR_MESSAGE);
        }
        if (documentDTO.getDescription().length() > DESCRIPTION_LENGTH_LIMIT) {
            throw new IllegalDescriptionLengthException(String.format("Description should be not longer than 100 " +
                    "symbols. Current description contains %d symbols.", documentDTO.getDescription().length()));
        }
    }
}
