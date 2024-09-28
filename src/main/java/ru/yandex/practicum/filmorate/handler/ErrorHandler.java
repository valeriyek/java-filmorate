package ru.yandex.practicum.filmorate.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exception.ResourceNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.util.Collections;
import java.util.Map;

@RestControllerAdvice
public class ErrorHandler {
    private static final String ERROR_KEY = "error";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = "Некорректный запрос.";
        if (ex.getBindingResult().getFieldError() != null) {
            errorMessage = ex.getBindingResult().getFieldError().getDefaultMessage();
        }
        return new ResponseEntity<>(Collections.singletonMap(ERROR_KEY, errorMessage), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String, String>> handleCustomValidationExceptions(ValidationException ex) {
        return new ResponseEntity<>(Collections.singletonMap(ERROR_KEY, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(Collections.singletonMap(ERROR_KEY, ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleAllUnhandledExceptions(Exception ex) {
        return new ResponseEntity<>(Collections.singletonMap(ERROR_KEY, "Внутренняя ошибка сервера"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
