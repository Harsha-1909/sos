package com.sos.application.validator;

import com.sos.application.entity.SosUser;
import com.sos.application.exception.BadRequestBodyException;
import com.sos.application.model.sosUser.request.UpdateSosUserRequest;
import com.sos.application.model.zone.request.ZoneRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SosUserValidatorImpl implements SosUserValidator {

    private final static Logger logger = LoggerFactory.getLogger(SosUserValidatorImpl.class);

    @Autowired
    private ZoneValidator zoneValidator;

    @Override
    public void validateNameAndPhoneNumberInRequestBody(SosUser sosUser) throws BadRequestBodyException {
        logger.info("Validating name and phoneNumber in SosUser: {}", sosUser);
        if (sosUser == null) {
            throw new BadRequestBodyException("Request body required");
        } else if (sosUser.getName() == null) {
            throw new BadRequestBodyException("name should not be null");
        } else if (sosUser.getName().isEmpty()) {
            throw new BadRequestBodyException("name should not be empty");
        } else if (sosUser.getPhoneNumber() == null) {
            throw new BadRequestBodyException("phoneNumber should not be null");
        } else if (sosUser.getPhoneNumber().isEmpty()) {
            throw new BadRequestBodyException("phoneNumber should not be empty");
        } else if (sosUser.getPhoneNumber().length() != 10) {
            throw new BadRequestBodyException("phoneNumber should be of length 10");
        }

        String phoneNumber = sosUser.getPhoneNumber();
        for (int ind = 0; ind <phoneNumber.length(); ind++) {
            if(!Character.isDigit(phoneNumber.charAt(ind))) {
                throw new BadRequestBodyException("phoneNumber should only contain numbers");
            }
        }
    }

    @Override
    public void validateUpdateSosUserRequest(UpdateSosUserRequest updateSosUserRequest) throws BadRequestBodyException {
        String name = updateSosUserRequest.getName();
        ZoneRequest zoneRequest = updateSosUserRequest.getZoneRequest();

        if (name == null && zoneRequest == null) {
            throw new BadRequestBodyException("Either name or zoneRequest should be present");
        }

        if (name != null && name.isEmpty()) {
            throw new BadRequestBodyException("name should not be empty");
        }

        if(zoneRequest != null) {
            zoneValidator.validateZoneRequestForFetchingZoneId(zoneRequest);
        }
    }
}
