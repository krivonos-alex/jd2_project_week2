package ru.mail.krivonos.al.repository.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.mail.krivonos.al.repository.DocumentRepository;
import ru.mail.krivonos.al.repository.connection.ConnectionHandler;
import ru.mail.krivonos.al.repository.exceptions.DatabaseConnectionException;
import ru.mail.krivonos.al.repository.exceptions.DatabaseInsertQueryException;
import ru.mail.krivonos.al.repository.exceptions.DatabaseSelectQueryException;
import ru.mail.krivonos.al.repository.exceptions.DatabaseUpdateQueryException;
import ru.mail.krivonos.al.repository.exceptions.ResultExtractionException;
import ru.mail.krivonos.al.repository.model.Document;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static ru.mail.krivonos.al.repository.constants.RepositoryErrorMessageConstants.*;


@Repository("documentRepository")
public class DocumentRepositoryImpl implements DocumentRepository {

    private static final Logger logger = LogManager.getLogger(DocumentRepositoryImpl.class);
    private final ConnectionHandler connectionHandler;

    @Autowired
    public DocumentRepositoryImpl(ConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
    }

    @Override
    public Document add(Document document) {
        try (Connection connection = connectionHandler.getConnection()) {
            connection.setAutoCommit(false);
            String sql = "INSERT INTO Document(unique_number, description) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, document.getUniqueNumber());
                statement.setString(2, document.getDescription());
                statement.execute();
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    Document documentWithID = getDocumentWithID(resultSet, document);
                    connection.commit();
                    return documentWithID;
                }
            } catch (Exception e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new DatabaseInsertQueryException(String.format(QUERY_EXCEPTION_ERROR_MESSAGE, sql), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseConnectionException(DATABASE_CONNECTION_ERROR_MESSAGE, e);
        }
    }

    @Override
    public Document findDocumentByID(Long id) {
        try (Connection connection = connectionHandler.getConnection()) {
            connection.setAutoCommit(false);
            String sql = "SELECT id, unique_number, description FROM Document WHERE id = ? AND deleted = FALSE";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setLong(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    Document document = getDocument(resultSet);
                    connection.commit();
                    return document;
                }
            } catch (Exception e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new DatabaseSelectQueryException(String.format(QUERY_EXCEPTION_ERROR_MESSAGE, sql), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseConnectionException(DATABASE_CONNECTION_ERROR_MESSAGE, e);
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = connectionHandler.getConnection()) {
            connection.setAutoCommit(false);
            String sql = "UPDATE Document SET deleted = TRUE WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setLong(1, id);
                int updated = statement.executeUpdate();
                connection.commit();
                logger.info(String.format("%d document deleted for id: %d", updated, id));
            } catch (Exception e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new DatabaseUpdateQueryException(String.format(QUERY_EXCEPTION_ERROR_MESSAGE, sql), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseConnectionException(DATABASE_CONNECTION_ERROR_MESSAGE, e);
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
            throw new ResultExtractionException(RESULT_EXTRACTION_ERROR_MESSAGE, e);
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
            throw new ResultExtractionException(RESULT_EXTRACTION_ERROR_MESSAGE, e);
        }
    }
}
