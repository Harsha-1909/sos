package com.sos.application.serviceprovider.controller;

import com.sos.application.exception.BadRequestBodyException;
import com.sos.application.model.serviceProvider.request.CreateServiceProviderRequest;
import com.sos.application.model.serviceProvider.response.CreateServiceProviderResponse;
import com.sos.application.service.ServiceProviderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceProviderAccountController {

    private static final Logger logger = LoggerFactory.getLogger(ServiceProviderAccountController.class);

    @Autowired
    private ServiceProviderService serviceProviderService;

    @PostMapping("/service-providers")
    public ResponseEntity<?> createAccountNameAndPhoneNumber(@RequestBody CreateServiceProviderRequest createServiceProviderRequest) {
        logger.info("Received post request for creating ServiceProvider with CreateServiceProviderRequest: {}",createServiceProviderRequest);
        try {
            CreateServiceProviderResponse createServiceProviderResponse = serviceProviderService.createAccountNameAndPhoneNumber(createServiceProviderRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(createServiceProviderResponse);
        } catch (BadRequestBodyException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
