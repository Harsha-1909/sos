package com.sos.application.validator;

import com.sos.application.entity.SosUser;
import com.sos.application.exception.BadRequestBodyException;
import com.sos.application.model.sosUser.request.UpdateSosUserRequest;

public interface SosUserValidator {

    void validateNameAndPhoneNumberInRequestBody(SosUser sosUser) throws BadRequestBodyException;

    void validateUpdateSosUserRequest(UpdateSosUserRequest updateSosUserRequest) throws BadRequestBodyException;
}
