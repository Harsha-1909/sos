package com.sos.application.service;

import com.sos.application.entity.SosUser;
import com.sos.application.exception.BadRequestBodyException;
import com.sos.application.repository.SosUserRepository;
import com.sos.application.validator.SosUserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SosUserServiceImpl implements SosUserService{

    private final static Logger logger = LoggerFactory.getLogger(SosUserServiceImpl.class);

    @Autowired
    private SosUserValidator sosUserValidator;

    @Autowired
    private SosUserRepository sosUserRepository;

    @Override
    public SosUser createSosUser(SosUser sosUserRequest) throws BadRequestBodyException {
        logger.info("In createSosUser for SosUser: {}", sosUserRequest);
        sosUserValidator.validateNameAndPhoneNumberInRequestBody(sosUserRequest);
        checkPhoneNumberExists(sosUserRequest.getPhoneNumber());

        SosUser sosUser = sosUserRepository.save(sosUserRequest);
        logger.debug("Created new SosUser: {}", sosUser);
        return sosUser;
    }

    private void checkPhoneNumberExists(String phoneNumber) throws BadRequestBodyException {
        logger.info("Checking if phoneNumber: {} already exists", phoneNumber);
        Optional<SosUser> sosUser = sosUserRepository.isPhoneNumberExists(phoneNumber);
        if(sosUser.isPresent()) {
            throw new BadRequestBodyException("phoneNumber already exists");
        }
    }

}
