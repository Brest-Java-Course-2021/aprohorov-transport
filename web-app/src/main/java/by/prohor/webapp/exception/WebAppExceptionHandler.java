package by.prohor.webapp.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Created by Artsiom Prokharau 06.04.2021
 */

@ControllerAdvice
public class WebAppExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(HttpClientErrorException.class)
    protected String handleDuplicateEntityInDb(HttpClientErrorException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "error";
    }


}
