package com.sos.application.validator;

import com.sos.application.entity.SosUser;
import com.sos.application.exception.BadRequestBodyException;

public interface SosUserValidator {

    void validateNameAndPhoneNumberInRequestBody(SosUser sosUser) throws BadRequestBodyException;

}
