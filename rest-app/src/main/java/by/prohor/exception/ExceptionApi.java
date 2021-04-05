package by.prohor.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Created by Artsiom Prokharau 03.04.2021
 */

public class ExceptionApi {

    private String message;
    private String debugMessage;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> errors;

    public ExceptionApi(String debugMessage) {
        this.debugMessage = debugMessage;
    }

    public ExceptionApi(String message, String debugMessage) {
        this.message = message;
        this.debugMessage = debugMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDebugMessage() {
        return debugMessage;
    }

    public void setDebugMessage(String debugMessage) {
        this.debugMessage = debugMessage;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}

