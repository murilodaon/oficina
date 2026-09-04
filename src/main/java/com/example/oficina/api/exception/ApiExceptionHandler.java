package com.example.oficina.api.exception;

import com.example.oficina.exception.RecursoDuplicadoException;
import com.example.oficina.exception.RecursoNaoEncontradoException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ApiError> tratarRecursoNaoEncontrado(
            RecursoNaoEncontradoException exception,
            HttpServletRequest request) {

        ApiError error = criarErro(
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                request.getRequestURI(),
                List.of()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    @ExceptionHandler(RecursoDuplicadoException.class)
    public ResponseEntity<ApiError> tratarRecursoDuplicado(
            RecursoDuplicadoException exception,
            HttpServletRequest request) {

        ApiError error = criarErro(
                HttpStatus.CONFLICT,
                exception.getMessage(),
                request.getRequestURI(),
                List.of()
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> tratarValidacao(
            MethodArgumentNotValidException exception,
            HttpServletRequest request) {

        List<String> details =
                exception.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(this::formatarErroDeCampo)
                        .toList();

        ApiError error = criarErro(
                HttpStatus.BAD_REQUEST,
                "Dados de entrada inválidos",
                request.getRequestURI(),
                details
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> tratarJsonInvalido(
            HttpMessageNotReadableException exception,
            HttpServletRequest request) {

        ApiError error = criarErro(
                HttpStatus.BAD_REQUEST,
                "JSON inválido ou malformado",
                request.getRequestURI(),
                List.of()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    private ApiError criarErro(
            HttpStatus status,
            String message,
            String path,
            List<String> details) {

        return new ApiError(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                path,
                details
        );
    }

    private String formatarErroDeCampo(
            FieldError fieldError) {

        return fieldError.getField()
                + ": "
                + fieldError.getDefaultMessage();
    }
}