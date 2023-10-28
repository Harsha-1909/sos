package com.sos.application.service;

import com.sos.application.exception.BadRequestBodyException;
import com.sos.application.exception.ResourceNotExistsException;
import com.sos.application.model.serviceProvider.request.CreateServiceProviderRequest;
import com.sos.application.model.serviceProvider.response.CreateServiceProviderResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ServiceProviderService {
    CreateServiceProviderResponse createAccountNameAndPhoneNumber(CreateServiceProviderRequest createServiceProviderRequest) throws BadRequestBodyException;

    void uploadAadhaar(MultipartFile aadhaarFile, Long serviceProviderId) throws BadRequestBodyException, ResourceNotExistsException, IOException;

    byte[] getAadhaarFile(Long serviceProviderId) throws ResourceNotExistsException, IOException;
}
