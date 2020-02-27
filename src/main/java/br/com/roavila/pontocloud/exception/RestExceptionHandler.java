package br.com.roavila.pontocloud.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String ERRO_INTERNO = "Ocorreu um erro inesperado.";

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<Object> handleUserInvalid(Exception ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NullPointerException.class})
    public ResponseEntity<Object> handleNullPointer(Exception ex, WebRequest request) {
        return new ResponseEntity<>(ERRO_INTERNO, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
