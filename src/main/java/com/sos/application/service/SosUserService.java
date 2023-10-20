package com.sos.application.service;

import com.sos.application.entity.SosUser;
import com.sos.application.exception.BadRequestBodyException;

public interface SosUserService {
    SosUser createSosUser(SosUser sosUser) throws BadRequestBodyException;

}
