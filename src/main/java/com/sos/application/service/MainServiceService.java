package com.sos.application.service;

import com.sos.application.entity.MainService;
import com.sos.application.entity.ServiceCategory;
import com.sos.application.exception.MethodParamViolationException;
import com.sos.application.exception.ResourceExistsException;
import com.sos.application.exception.ResourceNotExistsException;
import com.sos.application.model.services.ServiceWrapper;
import com.sos.application.repository.MainServiceRepository;
import com.sos.application.validator.MainServiceValidator;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MainServiceService {

    private final static Logger logger = LoggerFactory.getLogger(MainServiceService.class);

    @Autowired
    private MainServiceRepository mainServiceRepository;

    @Autowired
    private MainServiceValidator mainServiceValidator;

    @Autowired
    private ServiceCategoryService serviceCategoryService;

    public MainService createMainService(MainService mainService, Long serviceCategoryId) throws MethodParamViolationException {
        logger.info("started createMainService with mainService: {} and serviceCategoryId: {}", mainService, serviceCategoryId);

        mainServiceValidator.validateMainServiceName(mainService.getName());

        ServiceCategory serviceCategory;
         try {
             serviceCategory = serviceCategoryService.getServiceCategoryById(serviceCategoryId);
         } catch (ResourceNotExistsException e) {
             throw new MethodParamViolationException(e);
         }

        checkMainServiceNotExists(mainService.getName(), serviceCategoryId);

        mainService.setServiceCategory(serviceCategory);
        MainService mainServiceResource = mainServiceRepository.save(mainService);
        serviceCategory.addMainService(mainServiceResource);

        return mainServiceResource;
    }

    private void checkMainServiceNotExists(String mainServiceName, Long serviceCategoryId) throws MethodParamViolationException {
        logger.info("checking for existence of MainService with name: {} and ServiceCategory with Id: {}", mainServiceName, serviceCategoryId);
        Optional<MainService> mainService = findMainServiceByNameAndServiceCategoryId(mainServiceName, serviceCategoryId);
        if(mainService.isPresent()){
            try {
                throw new ResourceExistsException("MainService already exists with the provided mainService name and serviceCategory id");
            } catch (ResourceExistsException e){
                throw new MethodParamViolationException(e);
            }
        }
    }

    @Transactional
    private Optional<MainService> findMainServiceByNameAndServiceCategoryId(String mainServiceName, Long serviceCategoryId) {
        Optional<MainService> mainService = mainServiceRepository.findMainServiceByNameAndServiceCategoryId(mainServiceName, serviceCategoryId);
        return mainService;
    }

    public MainService getMainServiceById(Long mainServiceId) throws ResourceNotExistsException {
        logger.info("Fetching MainService with Id: {}", mainServiceId);
        Optional<MainService> mainService = mainServiceRepository.findById(mainServiceId);
        if (mainService.isEmpty()) {
            throw new ResourceNotExistsException("MainService does not exists with the provided Id");
        }
        return mainService.get();
    }

    public List<ServiceWrapper> getAllMainServicesAssociatedWithServiceCategoryId(Long serviceCategoryId) throws MethodParamViolationException {
        logger.info("validate ServiceCategoryId and fetch mainServices");
        try {
            ServiceCategory serviceCategory = serviceCategoryService.getServiceCategoryById(serviceCategoryId);
            logger.debug("Fetched ServiceCategory: {}", serviceCategory);
            List<MainService> mainServices = serviceCategory.getMainServices();
            logger.debug("Fetched list of MainServices using ServiceCategory.getMainServices(): {}", mainServices);
            List<ServiceWrapper> mainServiceWrappers = new ArrayList<>();
            for (MainService mainService : mainServices) {
                mainServiceWrappers.add(new ServiceWrapper(mainService.getId(), mainService.getName()));
            }
            return mainServiceWrappers;
        } catch (ResourceNotExistsException e) {
            throw new MethodParamViolationException(e);
        }
    }
}
