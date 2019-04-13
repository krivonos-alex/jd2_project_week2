package ru.mail.krivonos.al.repository.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import ru.mail.krivonos.al.repository.DocumentRepository;
import ru.mail.krivonos.al.repository.connection.ConnectionHandler;
import ru.mail.krivonos.al.repository.constants.RepositoryErrorMessageConstants;
import ru.mail.krivonos.al.repository.exceptions.DatabaseConnectionException;
import ru.mail.krivonos.al.repository.exceptions.ResultExtractionException;
import ru.mail.krivonos.al.repository.exceptions.StatementExecutionException;
import ru.mail.krivonos.al.repository.model.Document;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


@Repository("documentRepository")
public class DocumentRepositoryImpl implements DocumentRepository {

    private static final Logger logger = LogManager.getLogger(DocumentRepositoryImpl.class);
    private final ConnectionHandler connectionHandler;

    @Autowired
    public DocumentRepositoryImpl(
            @Qualifier("connectionHandler") ConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
    }

    @Override
    public Document add(Document document) {
        String sql = "INSERT INTO Document(unique_number, description) VALUES (?, ?)";
        try (Connection connection = connectionHandler.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, document.getUniqueNumber());
                statement.setString(2, document.getDescription());
                statement.execute();
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    connection.commit();
                    return getDocumentWithID(resultSet, document);
                }
            } catch (Exception e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new StatementExecutionException(RepositoryErrorMessageConstants.STATEMENT_EXECUTION_ERROR_MESSAGE, e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseConnectionException(RepositoryErrorMessageConstants.DATABASE_CONNECTION_ERROR_MESSAGE, e);
        }
    }

    @Override
    public Document findDocumentByID(Long id) {
        String sql = "SELECT id, unique_number, description FROM Document WHERE id = ? AND deleted = FALSE";
        try (Connection connection = connectionHandler.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setLong(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    connection.commit();
                    return getDocument(resultSet);
                }
            } catch (Exception e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new StatementExecutionException(RepositoryErrorMessageConstants.STATEMENT_EXECUTION_ERROR_MESSAGE, e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseConnectionException(RepositoryErrorMessageConstants.DATABASE_CONNECTION_ERROR_MESSAGE, e);
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "UPDATE Document SET deleted = TRUE WHERE id = ?";
        try (Connection connection = connectionHandler.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setLong(1, id);
                int updated = statement.executeUpdate();
                connection.commit();
                logger.info(String.format("%d document deleted for id: %d", updated, id));
            } catch (Exception e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new StatementExecutionException(RepositoryErrorMessageConstants.STATEMENT_EXECUTION_ERROR_MESSAGE, e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseConnectionException(RepositoryErrorMessageConstants.DATABASE_CONNECTION_ERROR_MESSAGE, e);
        }

    }

    private Document getDocument(ResultSet resultSet) {
        Document document = new Document();
        try {
            if (resultSet.next()) {
                document.setId(resultSet.getLong("id"));
                document.setUniqueNumber(resultSet.getString("unique_number"));
                document.setDescription(resultSet.getString("description"));
            }
            return document;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ResultExtractionException(RepositoryErrorMessageConstants.RESULT_EXTRACTION_ERROR_MESSAGE, e);
        }
    }

    private Document getDocumentWithID(ResultSet resultSet, Document document) {
        try {
            if (resultSet.next()) {
                document.setId(resultSet.getLong(1));
            }
            return document;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ResultExtractionException(RepositoryErrorMessageConstants.RESULT_EXTRACTION_ERROR_MESSAGE, e);
        }
    }
}
