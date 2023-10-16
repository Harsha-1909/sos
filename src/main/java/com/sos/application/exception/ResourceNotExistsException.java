package com.sos.application.exception;

public class ResourceNotExistsException extends Exception {
    public ResourceNotExistsException(String s) {
        super(s);
    }

    public ResourceNotExistsException(String s, Throwable cause) {
        super(s, cause);
    }
}
