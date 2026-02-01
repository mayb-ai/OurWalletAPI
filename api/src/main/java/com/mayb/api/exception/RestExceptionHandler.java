package com.mayb.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice // <--- Diz pro Spring: "Eu gerencio os erros de todos os Controllers"
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RuntimeException.class) // <--- Captura qualquer RuntimeException (nossos erros de regra de negócio)
    private ResponseEntity<RestErrorMessage> runtimeErrorHandler(RuntimeException exception) {

        RestErrorMessage threatResponse = new RestErrorMessage(
                HttpStatus.BAD_REQUEST.value(), // 400
                exception.getMessage()          // A mensagem que você escreveu no "throw new..."
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(threatResponse);
    }
}