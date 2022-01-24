package dev.franklinbg.sediservice.exception;

import dev.franklinbg.sediservice.utils.GenericResponse;
import org.hibernate.JDBCException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static dev.franklinbg.sediservice.utils.Global.RPTA_ERROR;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SpecificExceptionHandler {
    @ExceptionHandler(JDBCException.class)
    public GenericResponse sqlException(JDBCException ex) {
        ex.printStackTrace();
        return new GenericResponse<>("sql-exception", -1, ex.getMessage(), null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public GenericResponse validException(MethodArgumentNotValidException ex) {
        ex.printStackTrace();
        StringBuilder errDesc = new StringBuilder();
        if (ex.getBindingResult().getFieldErrors().size() > 1) {
            errDesc.append("se han encontrado valores no válidos para los siguientes campos\n ");
            for (FieldError err : ex.getBindingResult().getFieldErrors()) {
                errDesc.append("Message:").append(err.getDefaultMessage()).append("\n");
                errDesc.append("campo :").append(err.getField()).append(" valor:").append(err.getRejectedValue()).append("\n");
            }
        } else {
            errDesc.append("valor no válido para el atributo ").append(ex.getBindingResult().getFieldError().getField()).append("(").append(ex.getBindingResult().getFieldError().getRejectedValue()).append(")");
        }
        return new GenericResponse<>("valid-exception", RPTA_ERROR, errDesc.toString());
    }

}
