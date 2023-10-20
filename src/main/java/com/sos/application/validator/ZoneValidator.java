package com.sos.application.validator;

import com.sos.application.exception.BadRequestBodyException;
import com.sos.application.model.zone.request.ZoneRequest;

public interface ZoneValidator {
    void validateZoneRequestForFetchingZoneId(ZoneRequest zoneRequest) throws BadRequestBodyException;
}
