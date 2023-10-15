package com.sos.application.exception;

public class ResourceExistsException extends Exception {
    public ResourceExistsException(String s) {
        super(s);
    }

    public ResourceExistsException(String s, Throwable cause) {
        super(s, cause);
    }
}
