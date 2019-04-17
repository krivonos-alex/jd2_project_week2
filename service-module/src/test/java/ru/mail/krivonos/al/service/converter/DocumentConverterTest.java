package ru.mail.krivonos.al.service.converter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import ru.mail.krivonos.al.repository.model.Document;
import ru.mail.krivonos.al.service.converter.impl.DocumentConverterImpl;
import ru.mail.krivonos.al.service.model.DocumentDTO;

import java.util.UUID;


@RunWith(MockitoJUnitRunner.class)
public class DocumentConverterTest {

    private DocumentConverter documentConverter;

    @Before
    public void init() {
        documentConverter = new DocumentConverterImpl();
    }

    @Test
    public void shouldReturnDocumentWithSameUniqueNumberInFromDTOMethod() {
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setUniqueNumber(UUID.randomUUID().toString());
        Document document = documentConverter.fromDTO(documentDTO);
        Assert.assertEquals(documentDTO.getUniqueNumber(), document.getUniqueNumber());
    }

    @Test
    public void shouldReturnDocumentWithSameDescriptionInFromDTOMethod() {
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setDescription("Description");
        Document document = documentConverter.fromDTO(documentDTO);
        Assert.assertEquals(documentDTO.getDescription(), document.getDescription());
    }

    @Test
    public void shouldReturnDocumentWithSameIDInToDTOMethod() {
        Document document = new Document();
        document.setId(1L);
        DocumentDTO documentDTO = documentConverter.toDTO(document);
        Assert.assertEquals(document.getId(), documentDTO.getId());
    }

    @Test
    public void shouldReturnDocumentWithSameUniqueNumberInToDTOMethod() {
        Document document = new Document();
        document.setUniqueNumber(UUID.randomUUID().toString());
        DocumentDTO documentDTO = documentConverter.toDTO(document);
        Assert.assertEquals(document.getUniqueNumber(), documentDTO.getUniqueNumber());
    }

    @Test
    public void shouldReturnDocumentWithSameDescriptionInToDTOMethod() {
        Document document = new Document();
        document.setDescription("Description");
        DocumentDTO documentDTO = documentConverter.toDTO(document);
        Assert.assertEquals(document.getDescription(), documentDTO.getDescription());
    }
}
