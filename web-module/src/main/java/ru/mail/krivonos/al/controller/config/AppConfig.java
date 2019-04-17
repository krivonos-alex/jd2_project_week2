package ru.mail.krivonos.al.controller.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:database.properties")
@ComponentScan(basePackages = {
        "ru.mail.krivonos.al.controller",
        "ru.mail.krivonos.al.service",
        "ru.mail.krivonos.al.repository"})
@EnableAspectJAutoProxy
public class AppConfig {
}
