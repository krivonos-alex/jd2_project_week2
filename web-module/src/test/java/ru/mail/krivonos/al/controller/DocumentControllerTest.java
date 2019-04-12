package ru.mail.krivonos.al.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.mail.krivonos.al.controller.config.ControllerConfig;
import ru.mail.krivonos.al.controller.exceptions.*;
import ru.mail.krivonos.al.services.DocumentService;
import ru.mail.krivonos.al.services.model.DocumentDTO;

import java.util.UUID;

@ActiveProfiles("controller-test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DocumentControllerTestConfig.class, ControllerConfig.class})
public class DocumentControllerTest {

    @Autowired
    @Qualifier("documentController")
    private DocumentController documentController;

    @Autowired
    @Qualifier("documentService")
    private DocumentService documentService;

    @Test
    public void shouldReturnDocumentDTOWithIDAfterAdd() {
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setUniqueNumber(UUID.randomUUID().toString());
        documentDTO.setDescription("Description");
        DocumentDTO returningDTO = new DocumentDTO();
        returningDTO.setId(1L);
        Mockito.when(documentService.add(documentDTO)).thenReturn(returningDTO);
        long returnedID = documentController.add(documentDTO).getId();
        Assert.assertEquals(1L, returnedID);
    }

    @Test(expected = NullArgumentDocumentDTOException.class)
    public void shouldThrowNullArgumentDocumentDTOExceptionIfAddArgumentIsNull() {
        documentController.add(null);
    }

    @Test(expected = NullDocumentDTOIDException.class)
    public void shouldThrowNullDocumentIDExceptionIfReturningFromAddMethodDocumentDTOHaveNullID() {
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setDescription("Description");
        documentDTO.setUniqueNumber(UUID.randomUUID().toString());
        Mockito.when(documentService.add(documentDTO)).thenReturn(documentDTO);
        documentController.add(documentDTO);
    }

    @Test(expected = IllegalUniqueNumberFormatException.class)
    public void shouldThrowIllegalUniqueNumberFormatExceptionForNonUUIDFormatUniqueNumber() {
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setUniqueNumber("Es es");
        documentDTO.setId(1L);
        documentDTO.setDescription("Description");
        Mockito.when(documentService.add(documentDTO)).thenReturn(documentDTO);
        documentController.add(documentDTO);
    }

    @Test(expected = NullDocumentDTODescriptionException.class)
    public void shouldThrowNullDocumentDTODescriptionExceptionForNullDescription() {
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setUniqueNumber(UUID.randomUUID().toString());
        documentDTO.setId(1L);
        Mockito.when(documentService.add(documentDTO)).thenReturn(documentDTO);
        documentController.add(documentDTO);
    }

    @Test(expected = IllegalDescriptionLengthException.class)
    public void shouldThrowIllegalDescriptionLengthExceptionForDescriptionLongerThan100Symbols() {
        String description = "11111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111";
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setUniqueNumber(UUID.randomUUID().toString());
        documentDTO.setId(1L);
        documentDTO.setDescription(description);
        Mockito.when(documentService.add(documentDTO)).thenReturn(documentDTO);
        documentController.add(documentDTO);
    }

    @Test(expected = NullReturningDocumentDTOException.class)
    public void shouldThrowNullReturningDocumentDTOExceptionIfServiceMethodReturnsNullAfterAddMethod() {
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setUniqueNumber(UUID.randomUUID().toString());
        documentDTO.setId(1L);
        documentDTO.setDescription("Description");
        Mockito.when(documentService.add(documentDTO)).thenReturn(null);
        documentController.add(documentDTO);
    }

    @Test
    public void shouldReturnDocumentDTOWithSameIDForGetDocumentByIDMethod() {
        DocumentDTO returningDTO = new DocumentDTO();
        returningDTO.setUniqueNumber(UUID.randomUUID().toString());
        returningDTO.setDescription("Description");
        returningDTO.setId(1L);
        Mockito.when(documentService.getDocumentById(1L)).thenReturn(returningDTO);
        long returnedID = documentController.getDocumentById(1L).getId();
        Assert.assertEquals(1L, returnedID);
    }

    @Test(expected = NullIDArgumentException.class)
    public void shouldThrowNullIDArgumentExceptionForNullArgumentInGetDocumentByIDMethod() {
        documentController.getDocumentById(null);
    }

    @Test(expected = NullReturningDocumentDTOException.class)
    public void shouldThrowNullReturningDocumentDTOExceptionIfServiceMethodReturnsNullAfterGetDocumentByIDMethod() {
        Mockito.when(documentService.getDocumentById(1L)).thenReturn(null);
        documentController.getDocumentById(1L);
    }

    @Test(expected = NullDocumentDTOIDException.class)
    public void shouldThrowNullDocumentIDExceptionIfReturningFromGetDocumentByIDMethodDocumentDTOHaveNullID() {
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setDescription("Description");
        documentDTO.setUniqueNumber(UUID.randomUUID().toString());
        Mockito.when(documentService.getDocumentById(1L)).thenReturn(documentDTO);
        documentController.getDocumentById(1L);
    }

    @Test(expected = NullIDArgumentException.class)
    public void shouldThrowNullIDArgumentExceptionForNullArgumentInDeleteMethod() {
        documentController.delete(null);
    }

}
