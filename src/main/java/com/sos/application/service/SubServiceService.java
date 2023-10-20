package com.sos.application.service;

import com.sos.application.entity.SubService;
import com.sos.application.exception.MethodParamViolationException;
import com.sos.application.model.services.SubServiceResponse;

import java.util.List;

public interface SubServiceService {
    void createSubService (Long serviceCategoryId, Long mainServiceId, SubService subService) throws MethodParamViolationException;

    void deleteSubService(Long serviceCategoryId, Long mainServiceId, Long subServiceId) throws MethodParamViolationException;

    List<SubServiceResponse> getSubServicesByMainServiceId(Long serviceCategoryId, Long mainServiceId) throws MethodParamViolationException;

}
