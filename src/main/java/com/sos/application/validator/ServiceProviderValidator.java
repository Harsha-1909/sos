package com.sos.application.validator;

import com.sos.application.exception.BadRequestBodyException;

public interface ServiceProviderValidator {
    void validateNameAndPhoneNumber(String name, String phoneNumber) throws BadRequestBodyException;
}
