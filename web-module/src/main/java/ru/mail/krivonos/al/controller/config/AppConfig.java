package ru.mail.krivonos.al.controller.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.mail.krivonos.al.repository.config.RepositoryConfig;
import ru.mail.krivonos.al.services.config.ServicesConfig;

@Configuration
@Import({ControllerConfig.class, ServicesConfig.class, RepositoryConfig.class})
public class AppConfig {
}
