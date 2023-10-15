package com.sos.application.exception;

public class MethodParamViolationException extends Exception{
    public  MethodParamViolationException(String s){
        super(s);
    }

    public MethodParamViolationException(Throwable cause) {
        super(cause);
    }

    public MethodParamViolationException(String s, Throwable cause){
        super(s,cause);
    }
}
