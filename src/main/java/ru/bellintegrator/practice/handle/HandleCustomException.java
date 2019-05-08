package ru.bellintegrator.practice.handle;


import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.bellintegrator.practice.exception.CustomException;
import ru.bellintegrator.practice.message.ResponseError;

/**
 * Обработка собственный исключений
 * в error текст ошибки
 */
@RestControllerAdvice
public class HandleCustomException {
    @ExceptionHandler(CustomException.class)
    public ResponseError handleResponseException(CustomException ex) {
        ResponseError error = new ResponseError(ex.getMessage());
        return error;
    }

}