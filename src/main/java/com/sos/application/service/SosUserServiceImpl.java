package com.sos.application.service;

import com.sos.application.entity.SosUser;
import com.sos.application.entity.Zone;
import com.sos.application.exception.BadRequestBodyException;
import com.sos.application.exception.MethodParamViolationException;
import com.sos.application.model.sosUser.response.SosUserResponse;
import com.sos.application.model.zone.request.ZoneRequest;
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

    @Autowired
    private ZoneService zoneService;

    @Override
    public SosUser createSosUser(SosUser sosUserRequest) throws BadRequestBodyException {
        logger.info("In createSosUser for SosUser: {}", sosUserRequest);
        sosUserValidator.validateNameAndPhoneNumberInRequestBody(sosUserRequest);
        checkPhoneNumberExists(sosUserRequest.getPhoneNumber());

        SosUser sosUser = sosUserRepository.save(sosUserRequest);
        logger.debug("Created new SosUser: {}", sosUser);
        return sosUser;
    }

    @Override
    public SosUserResponse updateSosUserWithZone(ZoneRequest zoneRequest, Long userId) throws MethodParamViolationException, BadRequestBodyException {
        logger.info("In updateSosUserWithZone with ZoneRequest: {} and sosUserId: {}", zoneRequest, userId);
        SosUser sosUser = findById(userId);
        Zone zone = zoneService.getZone(zoneRequest);
        sosUser.setZone(zone);

        logger.debug("Updating SosUser");
        SosUser sosUserUpdated = sosUserRepository.save(sosUser);
        logger.debug("Updated Zone in SosUser: {}, SosUser.getZone(): {}", sosUserUpdated, sosUserUpdated.getZone());
        SosUserResponse sosUserResponse = new SosUserResponse(sosUserUpdated);
        logger.debug("created sosUserResponse: {}", sosUserResponse);
        return sosUserResponse;
    }

    @Override
    public SosUserResponse findBySosUserId(Long sosUserId) throws MethodParamViolationException {
        logger.info("In findBySosUserId with sosUserId: {}", sosUserId);
        SosUser sosUser = findById(sosUserId);
        SosUserResponse sosUserResponse = new SosUserResponse(sosUser);
        logger.debug("created sosUserResponse: {}", sosUserResponse);
        return sosUserResponse;
    }

    private SosUser findById(Long userId) throws MethodParamViolationException {
        logger.info("Fetching SosUser with sosUserId: {}", userId);
        Optional<SosUser> sosUser = sosUserRepository.findById(userId);
        logger.debug("Fetched Optional<SosUser>: {}", sosUser);
        if (sosUser.isEmpty()) {
            throw new  MethodParamViolationException("UserId not exists");
        }
        return sosUser.get();
    }

    private void checkPhoneNumberExists(String phoneNumber) throws BadRequestBodyException {
        logger.info("Checking if phoneNumber: {} already exists", phoneNumber);
        Optional<SosUser> sosUser = sosUserRepository.isPhoneNumberExists(phoneNumber);
        if(sosUser.isPresent()) {
            throw new BadRequestBodyException("phoneNumber already exists");
        }
    }

}
