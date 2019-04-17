package ru.mail.krivonos.al.controller.validator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import ru.mail.krivonos.al.controller.exceptions.IllegalDescriptionLengthException;
import ru.mail.krivonos.al.controller.exceptions.IllegalDocumentDTODescriptionException;
import ru.mail.krivonos.al.controller.exceptions.IllegalDocumentDTOIDException;
import ru.mail.krivonos.al.controller.exceptions.IllegalDocumentDTOStateException;
import ru.mail.krivonos.al.controller.exceptions.IllegalIDException;
import ru.mail.krivonos.al.controller.exceptions.IllegalReturningDocumentDTOException;
import ru.mail.krivonos.al.controller.exceptions.IllegalUniqueNumberFormatException;
import ru.mail.krivonos.al.controller.validator.impl.DocumentDTOValidatorImpl;
import ru.mail.krivonos.al.service.model.DocumentDTO;

import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class DocumentDTOValidatorTest {

    private DocumentDTOValidator documentDTOValidator;

    @Before
    public void init() {
        documentDTOValidator = new DocumentDTOValidatorImpl();
    }

    @Test(expected = IllegalDocumentDTOStateException.class)
    public void shouldThrowIllegalDocumentDTOStateExceptionIfDocumentDTOIsNullInValidateArgumentDocumentDTOMethod() {
        documentDTOValidator.validateArgumentDocumentDTO(null);
    }

    @Test(expected = IllegalDocumentDTOIDException.class)
    public void shouldThrowIllegalDocumentDTOIDExceptionIfDocumentDTOHaveNullIDInValidateReturningDocumentDTOMethod() {
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setDescription("Description");
        documentDTO.setUniqueNumber(UUID.randomUUID().toString());
        documentDTOValidator.validateReturningDocumentDTO(documentDTO);
    }


    @Test(expected = IllegalUniqueNumberFormatException.class)
    public void shouldThrowIllegalUniqueNumberFormatExceptionForNonUUIDFormatUniqueNumber() {
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setUniqueNumber("Es es");
        documentDTO.setId(1L);
        documentDTO.setDescription("Description");
        documentDTOValidator.validateArgumentDocumentDTO(documentDTO);
    }

    @Test(expected = IllegalDocumentDTODescriptionException.class)
    public void shouldThrowIllegalDocumentDTODescriptionExceptionForNullDescription() {
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setUniqueNumber(UUID.randomUUID().toString());
        documentDTO.setId(1L);
        documentDTOValidator.validateArgumentDocumentDTO(documentDTO);
    }

    @Test(expected = IllegalDescriptionLengthException.class)
    public void shouldThrowIllegalDescriptionLengthExceptionForDescriptionLongerThan100Symbols() {
        String description = "11111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111";
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setUniqueNumber(UUID.randomUUID().toString());
        documentDTO.setId(1L);
        documentDTO.setDescription(description);
        documentDTOValidator.validateArgumentDocumentDTO(documentDTO);
    }

    @Test(expected = IllegalReturningDocumentDTOException.class)
    public void shouldThrowIllegalReturningDocumentDTOExceptionIfDocumentDTOIsNullInValidateReturningDocumentDTOMethod() {
        documentDTOValidator.validateReturningDocumentDTO(null);
    }

    @Test(expected = IllegalIDException.class)
    public void shouldThrowIllegalIDExceptionForNullArgumentInValidateIDMethod() {
        documentDTOValidator.validateID(null);
    }
}
