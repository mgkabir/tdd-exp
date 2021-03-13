package io.kabir.validationexample.common;
import org.springframework.http.HttpStatus;

public enum ErrorCode {
    BAD_REQUEST ("DCC001",HttpStatus.BAD_REQUEST, "Bad Request.","Bad Request. Request have one or more invalid values"),
    NO_SUCH_ELEMENT ("DCC002", HttpStatus.NOT_FOUND, "No such Element","No Consents Found"),
    UNEXPECTED_STATE_EXCEPTION ( "DCC003", HttpStatus.INTERNAL_SERVER_ERROR, "Request Type mismatch","Request Type mismatch"),
    GENERIC_EXCEPTION( "DCC004", HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong", "Error caused due to unidentified reasons"),
    JSON_PARSER_ERROR ( "DCC005", HttpStatus.INTERNAL_SERVER_ERROR, "Invalid request format. Datatype mismatch","Invalid request format. Type mismatch in one or more values in request"),
    JSON_ERROR ( "DCC006", HttpStatus.INTERNAL_SERVER_ERROR, "Invalid request format. Unsupported format","Invalid request format. Type mismatch in one or more values in request"),
    INVALID_REQUEST_TYPE("DCC007", HttpStatus.BAD_REQUEST, "Invalid Request Type", "Please provide a valid Request"),
    NO_CONSENTS_FOUND("DCC008", HttpStatus.NOT_FOUND, "Resource Not Found", "No Consents Found");

    private final String code;
    private final HttpStatus statusCode;
    private final String message;
    private final String title;

    ErrorCode(String code, HttpStatus statusCode, String title, String message) {
        this.code = code;
        this.statusCode = statusCode;
        this.message = message;
        this.title = title;
    }

    public String getCode() {
        return code;
    }
    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public String getTitle() {
        return title;
    }
}