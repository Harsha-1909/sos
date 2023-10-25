package com.sos.application.service;

import com.sos.application.exception.BadRequestBodyException;
import com.sos.application.model.serviceProvider.request.CreateServiceProviderRequest;
import com.sos.application.model.serviceProvider.response.CreateServiceProviderResponse;

public interface ServiceProviderService {
    CreateServiceProviderResponse createAccountNameAndPhoneNumber(CreateServiceProviderRequest createServiceProviderRequest) throws BadRequestBodyException;
}
