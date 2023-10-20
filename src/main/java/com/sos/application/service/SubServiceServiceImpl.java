package com.sos.application.service;

import com.sos.application.entity.MainService;
import com.sos.application.entity.ServiceCategory;
import com.sos.application.entity.SubService;
import com.sos.application.exception.MethodParamViolationException;
import com.sos.application.exception.ResourceExistsException;
import com.sos.application.exception.ResourceNotExistsException;
import com.sos.application.model.services.SubServiceResponse;
import com.sos.application.repository.SubServiceRepository;
import com.sos.application.validator.SubServiceValidator;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class SubServiceServiceImpl implements SubServiceService {
    private final static Logger logger = LoggerFactory.getLogger(SubServiceServiceImpl.class);

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

            MainService mainService = mainServiceService.getMainServiceById(mainServiceId);
            checkMainServiceRelatedToServiceCategoryId(serviceCategoryId, mainService);
            checkSubServiceNotExists(subService.getName(), mainServiceId);

            subService.setMainService(mainService);
            SubService subServiceResource = subServiceRepository.save(subService);
            mainService.addSubService(subServiceResource);
            logger.debug("Created SubService: {}", subServiceResource);
        } catch (ResourceNotExistsException | ResourceExistsException e) {
            throw new MethodParamViolationException(e);
        }
    }

    private void checkMainServiceRelatedToServiceCategoryId(Long serviceCategoryId, MainService mainService) throws MethodParamViolationException {
        logger.info("validating if mainService: {} has serviceCategoryId: {}", mainService, serviceCategoryId);
        if(!Objects.equals(mainService.getServiceCategory().getId(), serviceCategoryId)) {
                throw new MethodParamViolationException("MainService is not related to ServiceCategory Id");
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

    public List<SubServiceResponse> getSubServicesByMainServiceId(Long serviceCategoryId, Long mainServiceId) throws MethodParamViolationException {
        logger.info("validate and fetch mainServiceId , check relation with ServiceCategoryId and fetch all subServices using mainService.getSubServices()");
        try {
            MainService mainService = mainServiceService.getMainServiceById(mainServiceId);
            logger.debug("Fetched mainService: {} using mainServiceId: {}", mainService, mainServiceId);
            checkMainServiceRelatedToServiceCategoryId(serviceCategoryId, mainService);

            List<SubService> subServices = mainService.getSubServices();
            logger.debug("Fetched list of SubServices using mainService.getSubServices(): {}", subServices);
            List<SubServiceResponse> subServiceResponses = new ArrayList<>();
            for (SubService subService: subServices) {
                subServiceResponses.add(new SubServiceResponse(subService.getId(), subService.getName()));
            }
            return subServiceResponses;
        } catch (ResourceNotExistsException e) {
            throw new MethodParamViolationException(e.getMessage(), e);
        }
    }
}
