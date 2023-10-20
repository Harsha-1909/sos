package com.sos.application.service;

import com.sos.application.entity.ServiceCategory;
import com.sos.application.exception.MethodParamViolationException;
import com.sos.application.exception.ResourceExistsException;
import com.sos.application.exception.ResourceNotExistsException;
import com.sos.application.model.services.ServiceCategoryResponse;
import com.sos.application.repository.ServiceCategoryRepository;

import com.sos.application.validator.ServiceCategoryValidator;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceCategoryServiceImpl implements ServiceCategoryService {

    private static final Logger logger = LoggerFactory.getLogger(ServiceCategoryServiceImpl.class);

    @Autowired
    private ServiceCategoryRepository serviceCategoryRepository;

    @Autowired
    private ServiceCategoryValidator serviceCategoryValidator;

    public ServiceCategory createServiceCategory(ServiceCategory serviceCategory) throws MethodParamViolationException {
        logger.info("Starting creation request for ServiceCategory: {}",serviceCategory);

        serviceCategoryValidator.validateServiceCategoryName(serviceCategory.getName());
        checkServiceCategoryNameNotExists(serviceCategory.getName());

        ServiceCategory serviceCategoryObj = serviceCategoryRepository.save(serviceCategory);

        logger.debug("Created new ServiceCategory: {}",serviceCategoryObj);
        return serviceCategoryObj;
    }

    private void checkServiceCategoryNameNotExists(String serviceCategoryName) throws MethodParamViolationException {
        logger.info("checking if ServiceCategory exists by name: {}", serviceCategoryName);
        Optional<ServiceCategory> serviceCategory = findServiceCategoryByName(serviceCategoryName);
        if(serviceCategory.isPresent()){
            try {
                throw new ResourceExistsException("ServiceCategory name already exists");
            } catch (ResourceExistsException e) {
                throw new MethodParamViolationException(e);
            }
        }
    }

    @Transactional
    private Optional<ServiceCategory> findServiceCategoryByName(String serviceCategoryName){
       return serviceCategoryRepository.findServiceCategoryByName(serviceCategoryName);
    }

    public ServiceCategory getServiceCategoryById(Long serviceCategoryId) throws ResourceNotExistsException {
        logger.info("Fetching ServiceCategory with Id: {}", serviceCategoryId);

        Optional<ServiceCategory> serviceCategory = findServiceCategoryById(serviceCategoryId);
        if(serviceCategory.isEmpty()){
                throw new ResourceNotExistsException("serviceCategory not exists with provided Id");
        }
            return serviceCategory.get();
    }

    private Optional<ServiceCategory> findServiceCategoryById(Long serviceCategoryId) {
        return serviceCategoryRepository.findById(serviceCategoryId);
    }

    @Override
    public List<ServiceCategoryResponse> getServiceCategories() {
       logger.info("Fetching all ServiceCategories");
       List<ServiceCategory> serviceCategories = serviceCategoryRepository.findAll();
       logger.debug("List of ServiceCategories :{}", serviceCategories);
       List<ServiceCategoryResponse> serviceCategoryResponses = new ArrayList<>();
       for (ServiceCategory serviceCategory: serviceCategories) {
           ServiceCategoryResponse serviceCategoryResponse = new ServiceCategoryResponse(serviceCategory.getId(), serviceCategory.getName());
           serviceCategoryResponses.add(serviceCategoryResponse);
       }
       logger.debug("Prepared ServiceCategoryResponses: {}",serviceCategoryResponses);
       return serviceCategoryResponses;
    }

}
