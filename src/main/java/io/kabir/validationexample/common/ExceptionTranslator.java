package io.kabir.validationexample.common;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ExceptionTranslator {
    private final Logger log = LoggerFactory.getLogger(ExceptionTranslator.class);


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("Error response from server: {}", ex.getMessage(), ex);
        String defaultMessage = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return getErrorResponseResponseEntity(HttpStatus.BAD_REQUEST, ErrorCode.BAD_REQUEST, defaultMessage);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.error("Error response from server: {}", ex.getMessage());
        String exMessage = ex.getMessage();
        String message = "Body must be present";
        if (StringUtils.isNotEmpty(exMessage) && exMessage.indexOf(":") != -1) {
            message = exMessage.substring(0, exMessage.indexOf(":"));
        }
        return getErrorResponseResponseEntity(HttpStatus.BAD_REQUEST, ErrorCode.BAD_REQUEST, message);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(ConstraintViolationException ex) {
        log.error("Error response from server: {}", ex.getMessage());
        String message = new ArrayList<>(ex.getConstraintViolations()).get(0).getMessageTemplate();
        return getErrorResponseResponseEntity(HttpStatus.BAD_REQUEST, ErrorCode.BAD_REQUEST, message);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        log.error("Error response from server: {}", ex.getMessage());

        return getErrorResponseResponseEntity(HttpStatus.BAD_REQUEST, ErrorCode.BAD_REQUEST, "Bad Date Format");
    }


    private ResponseEntity<ErrorResponse> getErrorResponseResponseEntity(HttpStatus httpStatus, ErrorCode errorCode, String message) {
        ErrorResponse errorResponse = new ErrorResponse();
        ErrorObject errorObject = new ErrorObject();
        errorObject.setCode(errorCode.getCode());
        errorObject.setStatusCode(httpStatus.value());
        errorObject.setTitle(errorCode.getTitle());
        errorObject.setDetail(message == null ? errorCode.getMessage() : message);
        List<ErrorObject> errors = new ArrayList<>();
        errors.add(errorObject);
        errorResponse.setErrors(errors);
        return new ResponseEntity<>(errorResponse, httpStatus);
    }
}
