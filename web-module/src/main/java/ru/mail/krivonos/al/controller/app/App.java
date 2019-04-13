package ru.mail.krivonos.al.controller.app;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.mail.krivonos.al.controller.DocumentController;
import ru.mail.krivonos.al.controller.config.AppConfig;
import ru.mail.krivonos.al.service.model.DocumentDTO;

import java.util.UUID;

public class App {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(AppConfig.class);
        ctx.refresh();

        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setDescription("Some description.");
        documentDTO.setUniqueNumber(UUID.randomUUID().toString());
        DocumentController documentController = ctx.getBean(DocumentController.class);
        DocumentDTO returnedDocumentDTO = documentController.add(documentDTO);
        DocumentDTO foundDocumentDTO = documentController.getDocumentById(returnedDocumentDTO.getId());
        documentController.delete(foundDocumentDTO.getId());

    }
}
