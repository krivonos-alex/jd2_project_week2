package ru.mail.krivonos.al.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import ru.mail.krivonos.al.controller.impl.DocumentControllerImpl;
import ru.mail.krivonos.al.controller.validator.DocumentDTOValidator;
import ru.mail.krivonos.al.service.DocumentService;
import ru.mail.krivonos.al.service.model.DocumentDTO;

import java.util.UUID;


@RunWith(MockitoJUnitRunner.class)
public class DocumentControllerTest {


    private DocumentController documentController;

    @Mock
    private DocumentService documentService;

    @Mock
    private DocumentDTOValidator documentDTOValidator;

    @Before
    public void init() {
        documentController = new DocumentControllerImpl(documentService, documentDTOValidator);
    }

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
}
