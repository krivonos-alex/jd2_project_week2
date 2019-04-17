package ru.mail.krivonos.al.repository.constants;

public class RepositoryErrorMessageConstants {

    private RepositoryErrorMessageConstants() {
    }

    public static final String QUERY_EXCEPTION_ERROR_MESSAGE = "Can't execute query: \"%s\"";

    public static final String INITIALISING_QUERY_EXCEPTION_ERROR_MESSAGE = "Something goes wrong while executing " +
            "query for database initialisation.";

    public static final String DATABASE_CONNECTION_ERROR_MESSAGE = "Can't create connection to database.";

    public static final String RESULT_EXTRACTION_ERROR_MESSAGE = "Error while extracting query result.";

    public static final String DATABASE_DRIVER_ERROR_MESSAGE = "Database driver class not found.";

    public static final String INITIAL_FILE_ERROR_MESSAGE = "Error while reading database initial file.";
}
