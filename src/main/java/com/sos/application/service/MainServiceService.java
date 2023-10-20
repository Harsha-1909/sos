package com.sos.application.service;

import com.sos.application.entity.MainService;
import com.sos.application.exception.MethodParamViolationException;
import com.sos.application.exception.ResourceNotExistsException;
import com.sos.application.model.services.MainServiceResponse;

import java.util.List;

public interface MainServiceService {
    MainService createMainService(MainService mainService, Long serviceCategoryId) throws MethodParamViolationException;

    MainService getMainServiceById(Long mainServiceId) throws ResourceNotExistsException;

    List<MainServiceResponse> getMainServicesByServiceCategoryId(Long serviceCategoryId) throws MethodParamViolationException;
}
