package ru.mail.krivonos.al.services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.mail.krivonos.al.repository.DocumentRepository;
import ru.mail.krivonos.al.repository.model.Document;
import ru.mail.krivonos.al.services.config.ServicesConfig;
import ru.mail.krivonos.al.services.converter.DocumentConverter;
import ru.mail.krivonos.al.services.exceptions.NullDocumentException;
import ru.mail.krivonos.al.services.model.DocumentDTO;

import java.util.UUID;

@ActiveProfiles("service-test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DocumentServiceTestConfig.class, ServicesConfig.class})
public class DocumentServiceTest {

    @Autowired
    @Qualifier("documentService")
    private DocumentService documentService;

    @Autowired
    @Qualifier("documentRepository")
    private DocumentRepository documentRepository;

    @Autowired
    @Qualifier("documentConverter")
    private DocumentConverter documentConverter;

    @Test
    public void shouldGetDocumentFromRepositoryAndReturnDocumentDTOWithIDInAddMethod() {
        DocumentDTO documentDTO = new DocumentDTO();
        Document returningDocument = new Document();
        Mockito.when(documentConverter.fromDTO(documentDTO)).thenReturn(returningDocument);
        Mockito.when(documentRepository.add(returningDocument)).thenReturn(returningDocument);
        documentDTO.setId(1L);
        Mockito.when(documentConverter.toDTO(returningDocument)).thenReturn(documentDTO);
        DocumentDTO returnedDTO = documentService.add(documentDTO);
        Assert.assertEquals(documentDTO.getId(), returnedDTO.getId());
    }

    @Test
    public void shouldGetDocumentFromRepositoryAndReturnDocumentDTOWithTheSameUniqueNumberInAddMethod() {
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setUniqueNumber(UUID.randomUUID().toString());
        Document returningDocument = new Document();
        returningDocument.setUniqueNumber(documentDTO.getUniqueNumber());
        Mockito.when(documentConverter.fromDTO(documentDTO)).thenReturn(returningDocument);
        Mockito.when(documentRepository.add(returningDocument)).thenReturn(returningDocument);
        Mockito.when(documentConverter.toDTO(returningDocument)).thenReturn(documentDTO);
        DocumentDTO returnedDTO = documentService.add(documentDTO);
        Assert.assertEquals(documentDTO.getUniqueNumber(), returnedDTO.getUniqueNumber());
    }

    @Test
    public void shouldGetDocumentFromRepositoryAndReturnDocumentDTOWithTheSameDescriptionInAddMethod() {
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setDescription("Description");
        Document returningDocument = new Document();
        returningDocument.setDescription(documentDTO.getDescription());
        Mockito.when(documentConverter.fromDTO(documentDTO)).thenReturn(returningDocument);
        Mockito.when(documentRepository.add(returningDocument)).thenReturn(returningDocument);
        Mockito.when(documentConverter.toDTO(returningDocument)).thenReturn(documentDTO);
        DocumentDTO returnedDTO = documentService.add(documentDTO);
        Assert.assertEquals(documentDTO.getDescription(), returnedDTO.getDescription());
    }

    @Test
    public void shouldReturnDocumentDTOWithTheSameIDInGetDocumentByIDMethod() {
        Document returningDocument = new Document();
        returningDocument.setId(1L);
        Mockito.when(documentRepository.findDocumentByID(1L)).thenReturn(returningDocument);
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setId(1L);
        Mockito.when(documentConverter.toDTO(returningDocument)).thenReturn(documentDTO);
        DocumentDTO returnedDTO = documentService.getDocumentById(1L);
        Assert.assertEquals(documentDTO.getId(), returnedDTO.getId());
    }

    @Test(expected = NullDocumentException.class)
    public void shouldThrowNullDocumentExceptionIfDocumentRepositoryReturnsNullInAddMethod() {
        DocumentDTO documentDTO = new DocumentDTO();
        Document returningDocument = new Document();
        Mockito.when(documentConverter.fromDTO(documentDTO)).thenReturn(returningDocument);
        Mockito.when(documentRepository.add(returningDocument)).thenReturn(null);
        Mockito.when(documentConverter.toDTO(returningDocument)).thenReturn(documentDTO);
        documentService.add(documentDTO);
    }

    @Test(expected = NullDocumentException.class)
    public void shouldThrowNullDocumentExceptionIfDocumentRepositoryReturnsNullInGetDocumentByIDMethod() {
        DocumentDTO documentDTO = new DocumentDTO();
        Document returningDocument = new Document();
        Mockito.when(documentRepository.findDocumentByID(1L)).thenReturn(null);
        Mockito.when(documentConverter.toDTO(returningDocument)).thenReturn(documentDTO);
        documentService.getDocumentById(1L);
    }
}
