package com.sos.application.validator;

import com.sos.application.exception.MethodParamViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SubServiceValidator {
    private final static Logger logger = LoggerFactory.getLogger(SubServiceValidator.class);
    public void validateSubServiceName(String name) throws MethodParamViolationException {
        logger.info("Starting input field validation for SubService name: {}", name);
        if (name == null) {
            throw new MethodParamViolationException("In Request Body name should not be NULL");
        } else if (name.isEmpty()) {
            throw new MethodParamViolationException("In Request Body name should not be EMPTY");
        }
    }
}
