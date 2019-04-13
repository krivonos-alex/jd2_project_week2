package ru.mail.krivonos.al.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.mail.krivonos.al.repository.DocumentRepository;
import ru.mail.krivonos.al.repository.model.Document;
import ru.mail.krivonos.al.service.DocumentService;
import ru.mail.krivonos.al.service.constants.ServiceErrorMessageConstants;
import ru.mail.krivonos.al.service.converter.DocumentConverter;
import ru.mail.krivonos.al.service.exceptions.DocumentNotFoundException;
import ru.mail.krivonos.al.service.exceptions.NullDocumentException;
import ru.mail.krivonos.al.service.model.DocumentDTO;

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
        checkDocumentForNulls(returnedDocument);
        return documentConverter.toDTO(returnedDocument);
    }

    @Override
    public DocumentDTO getDocumentById(Long id) {
        Document document = documentRepository.findDocumentByID(id);
        checkDocumentForNulls(document);
        return documentConverter.toDTO(document);
    }

    @Override
    public void delete(Long id) {
        documentRepository.delete(id);
    }

    private void checkDocumentForNulls(Document document) {
        if (document == null) {
            throw new NullDocumentException(ServiceErrorMessageConstants.NULL_DOCUMENT_ERROR_MESSAGE);
        }
        if (document.getId() == null) {
            throw new DocumentNotFoundException(ServiceErrorMessageConstants.DOCUMENT_NOT_FOUND_ERROR_MESSAGE);
        }
    }
}
