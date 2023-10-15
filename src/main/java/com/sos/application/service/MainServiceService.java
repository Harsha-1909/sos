package com.sos.application.service;

import com.sos.application.entity.MainService;
import com.sos.application.entity.ServiceCategory;
import com.sos.application.exception.MethodParamViolationException;
import com.sos.application.exception.ResourceExistsException;
import com.sos.application.repository.MainServiceRepository;
import com.sos.application.validator.MainServiceValidator;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        logger.info("Entered createMainService with mainService: {} and serviceCategoryId: {}", mainService, serviceCategoryId);

        mainServiceValidator.validateMainServiceName(mainService.getName());
        ServiceCategory serviceCategory = validateAndGetServiceCategoryById(serviceCategoryId);

        validateMainServiceNotExists(mainService.getName(), serviceCategoryId);

        mainService.setServiceCategory(serviceCategory);
        MainService mainServiceResource = mainServiceRepository.save(mainService);
        serviceCategory.addMainService(mainServiceResource);

        return mainServiceResource;
    }

    private ServiceCategory validateAndGetServiceCategoryById(Long serviceCategoryId) throws MethodParamViolationException {
        logger.info("validating serviceCategoryId: {}", serviceCategoryId);

        Optional<ServiceCategory> serviceCategory = serviceCategoryService.findServiceCategoryById(serviceCategoryId);
        if(serviceCategory.isEmpty()){
            throw new MethodParamViolationException("serviceCategoryId not exists");
        }
        return serviceCategory.get();
    }

    private void validateMainServiceNotExists(String mainServiceName, Long serviceCategoryId) throws MethodParamViolationException {
        logger.info("validating MainService ");
        Optional<MainService> mainService = findMainServiceByNameAndServiceCategoryId(mainServiceName, serviceCategoryId);
        if(mainService.isPresent()){
            try {
                throw new ResourceExistsException("MainService name already exists with the provided serviceCategory id");
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
}
