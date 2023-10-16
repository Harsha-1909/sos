package com.sos.application.service;

import com.sos.application.entity.MainService;
import com.sos.application.entity.ServiceCategory;
import com.sos.application.entity.SubService;
import com.sos.application.exception.MethodParamViolationException;
import com.sos.application.exception.ResourceExistsException;
import com.sos.application.exception.ResourceNotExistsException;
import com.sos.application.repository.SubServiceRepository;
import com.sos.application.validator.SubServiceValidator;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;


@Service
public class SubServiceService {
    private final static Logger logger = LoggerFactory.getLogger(SubServiceService.class);

    @Autowired
    private SubServiceRepository subServiceRepository;

    @Autowired
    private ServiceCategoryService serviceCategoryService;

    @Autowired
    private MainServiceService mainServiceService;

    @Autowired
    private SubServiceValidator subServiceValidator;

    public void createSubService (Long serviceCategoryId, Long mainServiceId, SubService subService) throws MethodParamViolationException {
        logger.info("starting createSubService");
        try {
            subServiceValidator.validateSubServiceName(subService.getName());

            ServiceCategory serviceCategory = serviceCategoryService.getServiceCategoryById(serviceCategoryId);
            MainService mainService = mainServiceService.getMainServiceById(mainServiceId);
            if(!Objects.equals(mainService.getServiceCategory().getId(), serviceCategory.getId())) {
                throw new MethodParamViolationException("ServiceCategory Id is not related to MainServiceId");
            }

            checkSubServiceNotExists(subService.getName(), mainServiceId);

            subService.setMainService(mainService);
            SubService subServiceResource = subServiceRepository.save(subService);
            mainService.addSubService(subServiceResource);
            logger.debug("Created SubService: {}", subServiceResource);
        } catch (ResourceNotExistsException | ResourceExistsException e) {
            throw new MethodParamViolationException(e);
        }
    }

    @Transactional
    private void checkSubServiceNotExists(String subServiceName, Long mainServiceId) throws ResourceExistsException {
        logger.info("checking if SubService exists using subServiceName: {} and mainServiceId: {}", subServiceName, mainServiceId);
        Optional<SubService> subService = subServiceRepository.findBySubServiceNameAndMainServiceID(subServiceName, mainServiceId);
        if(subService.isPresent()){
            throw new ResourceExistsException("SubService already exists with provided subService name and mainService Id");
        }
    }

    public void deleteSubService(Long serviceCategoryId, Long mainServiceId, Long subServiceId) throws MethodParamViolationException {
        logger.info("starting deleteSubService");
        try {
            ServiceCategory serviceCategory = serviceCategoryService.getServiceCategoryById(serviceCategoryId);
            MainService mainService = mainServiceService.getMainServiceById(mainServiceId);
            if(!Objects.equals(mainService.getServiceCategory().getId(), serviceCategory.getId())) {
                throw new MethodParamViolationException("ServiceCategory Id is not related to MainServiceId");
            }

            Optional<SubService> subService = subServiceRepository.findById(subServiceId);
            if (subService.isEmpty()) {
                throw new ResourceNotExistsException("SubService does not exists with provided SubService Id");
            }

            subServiceRepository.deleteById(subServiceId);
            logger.debug("Delete SubService Successfully");
        } catch (ResourceNotExistsException e) {
            throw new MethodParamViolationException(e);
        }
    }
}
