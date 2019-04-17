package ru.mail.krivonos.al.controller.constants;

public class ControllerErrorMessageConstants {

    private ControllerErrorMessageConstants() {
    }

    public static final String NULL_ID_ERROR_MESSAGE = "ID argument is null.";

    public static final String NULL_RETURNING_ERROR_MESSAGE = "Service returned null value.";

    public static final String NULL_DTO_ID_ERROR_MESSAGE = "Returning DocumentDTO should contain ID.";

    public static final String NULL_ARGUMENT_ERROR_MESSAGE = "Argument is not presented.";

    public static final String NULL_DESCRIPTION_ERROR_MESSAGE = "Description should be presented.";
}
