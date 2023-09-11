package com.robertciotoiu.connection;

public class CaptchaValidationError extends Error{
    public CaptchaValidationError(String message) {
        super(message);
    }
}
