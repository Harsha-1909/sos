package com.sos.application.validator;

import com.sos.application.exception.MethodParamViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MainServiceValidator {

    private final static Logger logger = LoggerFactory.getLogger(MainServiceValidator.class);
    public void validateMainServiceName(String mainServiceName) throws MethodParamViolationException {
        logger.info("validating mainService name:{}", mainServiceName);
        if (mainServiceName == null) {
            throw new MethodParamViolationException("MainService name should not be null");
        } else if (mainServiceName.isEmpty()) {
            throw new MethodParamViolationException("MainService name should not be empty");
        }
    }

}
