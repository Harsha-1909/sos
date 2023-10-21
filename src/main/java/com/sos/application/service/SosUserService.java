package com.sos.application.service;

import com.sos.application.entity.SosUser;
import com.sos.application.exception.BadRequestBodyException;
import com.sos.application.exception.MethodParamViolationException;
import com.sos.application.model.sosUser.request.UpdateSosUserRequest;
import com.sos.application.model.sosUser.response.SosUserResponse;

public interface SosUserService {
    SosUser createSosUser(SosUser sosUser) throws BadRequestBodyException;

    SosUserResponse updateSosUser(UpdateSosUserRequest updateSosUserRequest, Long userId) throws MethodParamViolationException, BadRequestBodyException;

    SosUserResponse findBySosUserId(Long sosUserId) throws MethodParamViolationException;
}
