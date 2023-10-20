package com.sos.application.service;

import com.sos.application.entity.SosUser;
import com.sos.application.exception.BadRequestBodyException;
import com.sos.application.exception.MethodParamViolationException;
import com.sos.application.exception.ResourceNotExistsException;
import com.sos.application.model.sosUser.response.SosUserResponse;
import com.sos.application.model.zone.request.ZoneRequest;

public interface SosUserService {
    SosUser createSosUser(SosUser sosUser) throws BadRequestBodyException;

    SosUserResponse updateSosUserWithZone(ZoneRequest zoneRequest, Long userId) throws MethodParamViolationException, BadRequestBodyException;
}
