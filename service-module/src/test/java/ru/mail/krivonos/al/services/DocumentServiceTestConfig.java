package ru.mail.krivonos.al.services;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import ru.mail.krivonos.al.repository.DocumentRepository;
import ru.mail.krivonos.al.services.converter.DocumentConverter;

@Profile("service-test")
@Configuration
public class DocumentServiceTestConfig {

    @Bean("documentRepository")
    @Primary
    public DocumentRepository documentRepository() {
        return Mockito.mock(DocumentRepository.class);
    }

    @Bean("documentConverter")
    @Primary
    public DocumentConverter documentConverter() {
        return Mockito.mock(DocumentConverter.class);
    }
}
