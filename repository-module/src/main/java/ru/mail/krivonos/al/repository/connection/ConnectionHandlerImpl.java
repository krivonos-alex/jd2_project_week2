package ru.mail.krivonos.al.repository.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.mail.krivonos.al.repository.constants.RepositoryErrorMessageConstants;
import ru.mail.krivonos.al.repository.exceptions.DatabaseConnectionException;
import ru.mail.krivonos.al.repository.exceptions.DatabaseDriverException;
import ru.mail.krivonos.al.repository.exceptions.DatabaseInitialFileReadingException;
import ru.mail.krivonos.al.repository.exceptions.StatementExecutionException;
import ru.mail.krivonos.al.repository.properties.DatabaseProperties;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.stream.Stream;

@Component("connectionHandler")
public class ConnectionHandlerImpl implements ConnectionHandler {

    private static final Logger logger = LogManager.getLogger(ConnectionHandlerImpl.class);
    private final DatabaseProperties databaseProperties;

    @Autowired
    public ConnectionHandlerImpl(
            @Qualifier("databaseProperties") DatabaseProperties databaseProperties) {
        this.databaseProperties = databaseProperties;
        try {
            Class.forName(databaseProperties.getDatabaseDriverName());
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseDriverException(RepositoryErrorMessageConstants.DATABASE_DRIVER_ERROR_MESSAGE, e);
        }
    }

    @Override
    public Connection getConnection() {
        try {
            Properties properties = new Properties();
            properties.setProperty("user", databaseProperties.getDatabaseUsername());
            properties.setProperty("password", databaseProperties.getDatabasePassword());
            properties.setProperty("useUnicode", "true");
            return DriverManager.getConnection(databaseProperties.getDatabaseURL(), properties);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseConnectionException(RepositoryErrorMessageConstants.DATABASE_CONNECTION_ERROR_MESSAGE, e);
        }
    }

    @PostConstruct
    public void initializeDatabase() {
        String initialFileName = getClass().getResource("/" + databaseProperties.getDatabaseInitialFile()).getPath();
        String[] databaseInitialQueries = getQueries(initialFileName);
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try (Statement statement = connection.createStatement()) {
                for (String databaseInitialQuery : databaseInitialQueries) {
                    statement.addBatch(databaseInitialQuery);
                }
                statement.executeBatch();
                connection.commit();
            } catch (Exception e) {
                connection.rollback();
                throw new StatementExecutionException(RepositoryErrorMessageConstants.STATEMENT_EXECUTION_ERROR_MESSAGE, e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseConnectionException(RepositoryErrorMessageConstants.DATABASE_CONNECTION_ERROR_MESSAGE, e);
        }
    }

    private String[] getQueries(String initialFileName) {
        try (Stream<String> fileStream = Files.lines(Paths.get(initialFileName))) {
            return fileStream.reduce(String::concat).orElse("").split(";");
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseInitialFileReadingException(RepositoryErrorMessageConstants.INITIAL_FILE_ERROR_MESSAGE, e);
        }
    }
}
