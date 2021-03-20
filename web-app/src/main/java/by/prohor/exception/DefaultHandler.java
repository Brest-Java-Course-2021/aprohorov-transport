package by.prohor.exception;

import by.prohor.dao.exception.DuplicateEntityInDbException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by Artsiom Prokharau 19.03.2021
 */

@ControllerAdvice("by.prohor.controller")
public class DefaultHandler {

    @ExceptionHandler(DuplicateEntityInDbException.class)
    public String exDuplicateInDb(Model model,DuplicateEntityInDbException ex) {
        model.addAttribute("message", ex.getMessage());
        return "error";
    }
}
