package com.sos.application.validator;

import com.sos.application.exception.MethodParamViolationException;
import org.springframework.stereotype.Component;

@Component
public class SubServiceValidator {
    public void validateSubServiceName(String name) throws MethodParamViolationException {
        if (name == null) {
            throw new MethodParamViolationException("In Request Body name should not be NULL");
        } else if (name.isEmpty()) {
            throw new MethodParamViolationException("In Request Body name should not be EMPTY");
        }
    }
}
