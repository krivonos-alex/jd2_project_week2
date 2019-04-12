package ru.mail.krivonos.al.controller;


import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import ru.mail.krivonos.al.services.DocumentService;

@Profile("controller-test")
@Configuration
public class DocumentControllerTestConfig {

    @Bean("documentService")
    @Primary
    public DocumentService documentService() {
        return Mockito.mock(DocumentService.class);
    }
}
