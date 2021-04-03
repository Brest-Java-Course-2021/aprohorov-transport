package by.prohor.exception;

import by.prohor.dao.exception.DuplicateEntityInDbException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Created by Artsiom Prokharau 03.04.2021
 */

@ControllerAdvice
public class RestExceptionHandler  extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DuplicateEntityInDbException.class)
    protected ResponseEntity<Object> handleDuplicateEntityInDb(DuplicateEntityInDbException ex) {
        ExceptionApi exceptionApi = new ExceptionApi(ex.getMessage());
        return new ResponseEntity<>(exceptionApi, HttpStatus.CONFLICT);
    }
}
