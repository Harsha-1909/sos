package com.sos.application.service;

import com.sos.application.entity.ServiceProvider;
import com.sos.application.exception.BadRequestBodyException;
import com.sos.application.model.serviceProvider.request.CreateServiceProviderRequest;
import com.sos.application.model.serviceProvider.response.CreateServiceProviderResponse;
import com.sos.application.repository.ServiceProviderRepository;
import com.sos.application.validator.ServiceProviderValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ServiceProviderServiceImpl implements ServiceProviderService {

    private static final Logger logger = LoggerFactory.getLogger(ServiceProviderServiceImpl.class);

    @Autowired
    private ServiceProviderValidator serviceProviderValidator;

    @Autowired
    private ServiceProviderRepository serviceProviderRepository;

    @Override
    public CreateServiceProviderResponse createAccountNameAndPhoneNumber(CreateServiceProviderRequest createServiceProviderRequest) throws BadRequestBodyException {
        logger.debug("In createAccountNameAndPhoneNumber with CreateServiceProviderRequest: {}", createServiceProviderRequest);
        serviceProviderValidator.validateNameAndPhoneNumber(createServiceProviderRequest.getName(), createServiceProviderRequest.getPhoneNumber());
        checkPhoneNumberExists(createServiceProviderRequest.getPhoneNumber());

        ServiceProvider serviceProvider = createAndSaveServiceProvider(createServiceProviderRequest);
        CreateServiceProviderResponse createServiceProviderResponse = constructCreateServiceProviderResponse(serviceProvider);
        return createServiceProviderResponse;
    }

    private void checkPhoneNumberExists(String phoneNumber) throws BadRequestBodyException {
        logger.info("Checking if phoneNumber: {} exists or not", phoneNumber);
        Optional<ServiceProvider> serviceProvider = serviceProviderRepository.checkPhoneNumberExists(phoneNumber);
        if(serviceProvider.isPresent()) {
            throw new BadRequestBodyException(String.format("Account already exists with provided phoneNumber: %s", phoneNumber));
        }
    }

    private ServiceProvider createAndSaveServiceProvider(CreateServiceProviderRequest createServiceProviderRequest) {
        logger.info("Creating & saving ServiceProvider with CreateServiceProviderRequest: {}", createServiceProviderRequest);
        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.setName(createServiceProviderRequest.getName());
        serviceProvider.setPhoneNumber(createServiceProviderRequest.getPhoneNumber());
        serviceProvider.setHasVerified(false);
        serviceProvider.setCreatedTime(LocalDateTime.now());

        ServiceProvider createdServiceProvider = serviceProviderRepository.save(serviceProvider);
        logger.debug("created ServiceProvider:  {}", serviceProvider);

        return createdServiceProvider;
    }

    private CreateServiceProviderResponse constructCreateServiceProviderResponse(ServiceProvider serviceProvider) {
        logger.info("Constructing CreateServiceProviderResponse from ServiceProvider: {}", serviceProvider);
        CreateServiceProviderResponse createServiceProviderResponse = new CreateServiceProviderResponse();
        createServiceProviderResponse.setId(serviceProvider.getId());
        createServiceProviderResponse.setName(serviceProvider.getName());
        createServiceProviderResponse.setPhoneNumber(serviceProvider.getPhoneNumber());
        logger.debug("Constructed CreateServiceProviderResponse: {}", createServiceProviderResponse);
        return createServiceProviderResponse;
    }

}
