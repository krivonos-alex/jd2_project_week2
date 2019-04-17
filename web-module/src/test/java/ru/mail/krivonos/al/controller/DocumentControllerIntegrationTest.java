package ru.mail.krivonos.al.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import ru.mail.krivonos.al.controller.config.AppConfig;
import ru.mail.krivonos.al.service.exceptions.DocumentNotFoundException;
import ru.mail.krivonos.al.service.model.DocumentDTO;

import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class,
        loader = AnnotationConfigContextLoader.class)
public class DocumentControllerIntegrationTest {

    private static final String DESCRIPTION = "Some description.";

    @Autowired
    @Qualifier("documentController")
    DocumentController documentController;

    @Test
    public void shouldSaveDocumentAndReturnEqualsDocumentDTOWithID() {
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setUniqueNumber(UUID.randomUUID().toString());
        documentDTO.setDescription(DESCRIPTION);

        DocumentDTO returnedDocumentDTO = documentController.add(documentDTO);
        Assert.assertNotNull(returnedDocumentDTO.getId());
        Assert.assertEquals(documentDTO.getUniqueNumber(), returnedDocumentDTO.getUniqueNumber());
        Assert.assertEquals(documentDTO.getDescription(), returnedDocumentDTO.getDescription());
    }

    @Test
    public void shouldSaveDocumentAndReturnEqualsDocumentForGetByIDMethodWithProperID() {
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setUniqueNumber(UUID.randomUUID().toString());
        documentDTO.setDescription(DESCRIPTION);

        DocumentDTO returnedDocumentDTO = documentController.add(documentDTO);
        DocumentDTO documentById = documentController.getDocumentById(returnedDocumentDTO.getId());
        Assert.assertEquals(documentDTO.getUniqueNumber(), documentById.getUniqueNumber());
        Assert.assertEquals(documentDTO.getDescription(), documentById.getDescription());
    }

    @Test(expected = DocumentNotFoundException.class)
    public void shouldThrowDocumentNotFoundExceptionIfDocumentWithSuchIdIsNotFoundOrDeleted() {
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setUniqueNumber(UUID.randomUUID().toString());
        documentDTO.setDescription(DESCRIPTION);

        DocumentDTO returnedDocumentDTO = documentController.add(documentDTO);
        documentController.delete(returnedDocumentDTO.getId());
        documentController.getDocumentById(returnedDocumentDTO.getId());
    }


}
