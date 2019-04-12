package ru.mail.krivonos.al.services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.mail.krivonos.al.repository.model.Document;
import ru.mail.krivonos.al.services.config.ServicesConfig;
import ru.mail.krivonos.al.services.converter.DocumentConverter;
import ru.mail.krivonos.al.services.model.DocumentDTO;

import java.util.UUID;

@ActiveProfiles("documentConverter-test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServicesConfig.class, DocumentConverterTestConfig.class})
public class DocumentConverterTest {

    @Autowired
    @Qualifier("documentConverter")
    private DocumentConverter documentConverter;

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
