package ru.bellintegrator.practice.exception;

/**
 * Собственное исклечение
 * вызывается в service
 */
public class CustomException extends RuntimeException {

    public CustomException(String msg) {
        super(msg);
    }
}