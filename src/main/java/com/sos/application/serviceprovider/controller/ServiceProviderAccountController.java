package com.sos.application.serviceprovider.controller;

import com.sos.application.exception.BadRequestBodyException;
import com.sos.application.exception.ResourceNotExistsException;
import com.sos.application.model.serviceProvider.request.CreateServiceProviderRequest;
import com.sos.application.model.serviceProvider.response.CreateServiceProviderResponse;
import com.sos.application.service.ServiceProviderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

    @PutMapping("/service-providers/{serviceProviderId}")
    public ResponseEntity<?> uploadAadhaar(@RequestParam("file") MultipartFile file, @PathVariable Long serviceProviderId) {
        logger.info("Received put request to upload aadhaar file with serviceProviderId: {}", serviceProviderId);
        try {
            serviceProviderService.uploadAadhaar(file, serviceProviderId);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("File uploaded successfully");
        } catch (BadRequestBodyException | ResourceNotExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (IOException e) {
            logger.error("IOException : ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Something went wrong: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/service-providers/{serviceProviderId}")
    public ResponseEntity<?> getAadhaar(@PathVariable Long serviceProviderId) {
        logger.info("Received get request for aadhaar with serviceProviderId: {}", serviceProviderId);
        try {
            byte[] bytes = serviceProviderService.getAadhaarFile(serviceProviderId);
            return ResponseEntity.status(HttpStatus.OK).body(bytes);
        } catch (ResourceNotExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (IOException e) {
            logger.error("IOException : ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Something went wrong: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
