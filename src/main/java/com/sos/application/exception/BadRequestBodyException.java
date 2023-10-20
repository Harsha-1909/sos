package com.sos.application.exception;

public class BadRequestBodyException extends Exception {
    public BadRequestBodyException(String s) {
        super(s);
    }

    public BadRequestBodyException(String s, Throwable cause) {
        super(s, cause);
    }

    public BadRequestBodyException(Throwable cause) {
        super(cause);
    }
}
