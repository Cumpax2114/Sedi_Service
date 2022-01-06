package dev.franklinbg.sediservice.exception;

import dev.franklinbg.sediservice.utils.GenericResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GenericExceptionHandler {
    @ExceptionHandler(Exception.class)
    public GenericResponse genericException(Exception ex) {
        ex.printStackTrace();
        return new GenericResponse<>("exception", -1, ex.getMessage());
    }
}
