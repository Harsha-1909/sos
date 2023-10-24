package com.sos.application.validator;

import com.sos.application.exception.BadRequestBodyException;
import com.sos.application.exception.MethodParamViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ServiceCategoryValidator {

    private static final Logger logger = LoggerFactory.getLogger(ServiceCategoryValidator.class);

    public void validateServiceCategoryName(String serviceCategoryName) throws BadRequestBodyException {
        logger.info("Starting input field validation for ServiceCategory name: {}",serviceCategoryName);

        if (serviceCategoryName == null) {
            throw new BadRequestBodyException("name should not be null");
        } else if (serviceCategoryName.isEmpty()) {
            throw new BadRequestBodyException("name should not be empty");
        }

    }

}
