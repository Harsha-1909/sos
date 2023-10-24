package com.sos.application.service;

import com.sos.application.entity.ServiceCategory;
import com.sos.application.exception.BadRequestBodyException;
import com.sos.application.exception.MethodParamViolationException;
import com.sos.application.exception.ResourceNotExistsException;
import com.sos.application.model.services.ServiceCategoryResponse;

import java.util.List;
import java.util.Optional;

public interface ServiceCategoryService {
    ServiceCategory createServiceCategory(ServiceCategory serviceCategory) throws BadRequestBodyException;

    ServiceCategory getServiceCategoryById(Long serviceCategoryId) throws ResourceNotExistsException;

    List<ServiceCategoryResponse> getServiceCategories();
}
