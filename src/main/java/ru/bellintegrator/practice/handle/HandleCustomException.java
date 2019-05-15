package ru.bellintegrator.practice.handle;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.bellintegrator.practice.exception.CustomException;
import ru.bellintegrator.practice.message.ResponseError;
import ru.bellintegrator.practice.service.impl.OfficeServiceImpl;

/**
 * Обработка собственный исключений
 * возращает текст ошибки
 */
@RestControllerAdvice
public class HandleCustomException {
    private final Logger log = LoggerFactory.getLogger(OfficeServiceImpl.class);

    @ExceptionHandler(CustomException.class)
    public ResponseError handleResponseException(CustomException ex) {
        log.error("custom exception  ", ex);
        return new ResponseError(ex.getMessage());
    }

}