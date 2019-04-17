package ru.mail.krivonos.al.repository.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("databaseProperties")
public class DatabaseProperties {

    @Value("${database.driver.name}")
    private String databaseDriverName;
    @Value("${database.url}")
    private String databaseURL;
    @Value("${database.username:}")
    private String databaseUsername;
    @Value("${database.password:}")
    private String databasePassword;
    @Value("${database.initial.file}")
    private String databaseInitialFile;

    public String getDatabaseDriverName() {
        return databaseDriverName;
    }

    public String getDatabaseURL() {
        return databaseURL;
    }

    public String getDatabaseUsername() {
        return databaseUsername;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    public String getDatabaseInitialFile() {
        return databaseInitialFile;
    }
}
