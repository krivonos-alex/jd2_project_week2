package ru.mail.krivonos.al.services.converter;

import ru.mail.krivonos.al.repository.model.Document;
import ru.mail.krivonos.al.services.model.DocumentDTO;

public interface DocumentConverter {

    Document fromDTO(DocumentDTO documentDTO);

    DocumentDTO toDTO(Document document);
}
