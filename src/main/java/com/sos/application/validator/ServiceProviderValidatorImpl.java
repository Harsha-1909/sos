package com.sos.application.validator;

import com.sos.application.exception.BadRequestBodyException;
import com.sos.application.utils.Constant.SERVICE_PROVIDER;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ServiceProviderValidatorImpl implements ServiceProviderValidator {

    private static final Logger logger = LoggerFactory.getLogger(ServiceProviderValidatorImpl.class);

    @Override
    public void validateNameAndPhoneNumber(String name, String phoneNumber) throws BadRequestBodyException {
        logger.info("Validating name: {} and phoneNumber: {}", name, phoneNumber);

        Map<String, String> mandatoryValues = new HashMap<>();
        mandatoryValues.put(SERVICE_PROVIDER.NAME, name);
        mandatoryValues.put(SERVICE_PROVIDER.PHONE_NUMBER, phoneNumber);

        for(String key : mandatoryValues.keySet()) {
            String value = mandatoryValues.get(key);
            if(value == null) {
                throw new BadRequestBodyException(String.format("%s should not be null", key));
            } else if(value.isEmpty()) {
                throw new BadRequestBodyException(String.format("%s should not be empty", key));
            }
        }

        if(phoneNumber.length()!=10) {
            throw new BadRequestBodyException("phoneNumber should be of length 10");
        }

        for (int ind = 0; ind <phoneNumber.length(); ind++) {
            if(!Character.isDigit(phoneNumber.charAt(ind))) {
                throw new BadRequestBodyException("phoneNumber should only contain numbers");
            }
        }

    }
}
