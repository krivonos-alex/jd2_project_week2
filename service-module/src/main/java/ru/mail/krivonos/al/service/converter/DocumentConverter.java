package ru.mail.krivonos.al.service.converter;

import ru.mail.krivonos.al.repository.model.Document;
import ru.mail.krivonos.al.service.model.DocumentDTO;

public interface DocumentConverter {

    Document fromDTO(DocumentDTO documentDTO);

    DocumentDTO toDTO(Document document);
}
