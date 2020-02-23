package tech.marcusvieira.springbootpostgreselastic.errorhandlers;

import java.util.Date;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import tech.marcusvieira.springbootpostgreselastic.errorhandlers.exceptions.ProductNotFoundException;

@ControllerAdvice
@RestController
public class CustomizeResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        final ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
            request.getDescription(false));
        return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public final ResponseEntity<Object> handleProductNotFoundException(Exception ex, WebRequest request) {
        final ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
            request.getDescription(false));
        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }
}
