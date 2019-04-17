package ru.mail.krivonos.al.service.converter.impl;

import org.springframework.stereotype.Component;
import ru.mail.krivonos.al.repository.model.Document;
import ru.mail.krivonos.al.service.converter.DocumentConverter;
import ru.mail.krivonos.al.service.model.DocumentDTO;

@Component("documentConverter")
public class DocumentConverterImpl implements DocumentConverter {

    @Override
    public Document fromDTO(DocumentDTO documentDTO) {
        Document document = new Document();
        document.setUniqueNumber(documentDTO.getUniqueNumber());
        document.setDescription(documentDTO.getDescription());
        return document;
    }

    @Override
    public DocumentDTO toDTO(Document document) {
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setId(document.getId());
        documentDTO.setUniqueNumber(document.getUniqueNumber());
        documentDTO.setDescription(document.getDescription());
        return documentDTO;
    }
}
