package ru.mail.krivonos.al.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import ru.mail.krivonos.al.repository.DocumentRepository;
import ru.mail.krivonos.al.repository.model.Document;
import ru.mail.krivonos.al.service.converter.DocumentConverter;
import ru.mail.krivonos.al.service.exceptions.DocumentNotFoundException;
import ru.mail.krivonos.al.service.exceptions.NullDocumentException;
import ru.mail.krivonos.al.service.impl.DocumentServiceImpl;
import ru.mail.krivonos.al.service.model.DocumentDTO;

@RunWith(MockitoJUnitRunner.class)
public class DocumentServiceTest {


    private DocumentService documentService;

    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private DocumentConverter documentConverter;

    @Before
    public void init() {
        documentService = new DocumentServiceImpl(documentRepository, documentConverter);
    }

    @Test
    public void shouldGetDocumentFromRepositoryAndReturnDocumentDTOWithIDInAddMethod() {
        DocumentDTO documentDTO = new DocumentDTO();
        Document returningDocument = new Document();
        returningDocument.setId(1L);
        Mockito.when(documentConverter.fromDTO(documentDTO)).thenReturn(returningDocument);
        Mockito.when(documentRepository.add(returningDocument)).thenReturn(returningDocument);
        documentDTO.setId(1L);
        Mockito.when(documentConverter.toDTO(returningDocument)).thenReturn(documentDTO);
        DocumentDTO returnedDTO = documentService.add(documentDTO);
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

    @Test(expected = DocumentNotFoundException.class)
    public void shouldThrowDocumentNotFoundExceptionIfDocumentWithNullIdReturned() {
        DocumentDTO documentDTO = new DocumentDTO();
        Document returningDocument = new Document();
        Mockito.when(documentRepository.findDocumentByID(1L)).thenReturn(returningDocument);
        Mockito.when(documentConverter.toDTO(returningDocument)).thenReturn(documentDTO);
        documentService.getDocumentById(1L);
    }
}
