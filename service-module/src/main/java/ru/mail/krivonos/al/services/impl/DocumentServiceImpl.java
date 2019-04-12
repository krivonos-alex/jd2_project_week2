package ru.mail.krivonos.al.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.mail.krivonos.al.repository.DocumentRepository;
import ru.mail.krivonos.al.repository.model.Document;
import ru.mail.krivonos.al.services.DocumentService;
import ru.mail.krivonos.al.services.converter.DocumentConverter;
import ru.mail.krivonos.al.services.exceptions.NullDocumentException;
import ru.mail.krivonos.al.services.model.DocumentDTO;

@Service("documentService")
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;

    private final DocumentConverter documentConverter;

    @Autowired
    public DocumentServiceImpl(
            @Qualifier("documentRepository") DocumentRepository documentRepository,
            @Qualifier("documentConverter") DocumentConverter documentConverter
    ) {
        this.documentRepository = documentRepository;
        this.documentConverter = documentConverter;
    }

    @Override
    public DocumentDTO add(DocumentDTO documentDTO) {
        Document convertedDocument = documentConverter.fromDTO(documentDTO);
        Document returnedDocument = documentRepository.add(convertedDocument);
        checkDocumentForNull(returnedDocument);
        return documentConverter.toDTO(returnedDocument);
    }

    @Override
    public DocumentDTO getDocumentById(Long id) {
        Document document = documentRepository.findDocumentByID(id);
        checkDocumentForNull(document);
        return documentConverter.toDTO(document);
    }

    @Override
    public void delete(Long id) {
        documentRepository.delete(id);
    }

    private void checkDocumentForNull(Document document) {
        if (document == null) {
            throw new NullDocumentException("Received Document is null.");
        }
    }
}
