package dev.franklinbg.sediservice.exception;

import dev.franklinbg.sediservice.utils.GenericResponse;
import org.hibernate.JDBCException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import static dev.franklinbg.sediservice.utils.Global.RPTA_ERROR;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SpecificExceptionHandler {
    @ExceptionHandler(JDBCException.class)
    public GenericResponse sqlException(JDBCException ex){
        return new GenericResponse<>("sql-exception", -1,ex.getMessage(),null);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public GenericResponse validException(MethodArgumentNotValidException ex){
        return new GenericResponse<>("valid-exception", RPTA_ERROR, ex.getMessage());
    }

}
