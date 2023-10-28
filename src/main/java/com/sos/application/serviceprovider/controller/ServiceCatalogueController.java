package com.sos.application.serviceprovider.controller;

import com.sos.application.exception.MethodParamViolationException;
import com.sos.application.model.services.MainServiceResponse;
import com.sos.application.model.services.ServiceCategoryResponse;
import com.sos.application.model.services.SubServiceResponse;
import com.sos.application.service.MainServiceService;
import com.sos.application.service.ServiceCategoryService;
import com.sos.application.service.SubServiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ServiceCatalogueController {

    private static final Logger logger = LoggerFactory.getLogger(ServiceCatalogueController.class);
    @Autowired
    private ServiceCategoryService serviceCategoryService;
    @Autowired
    private MainServiceService mainServiceService;
    @Autowired
    private SubServiceService subServiceService;

    @GetMapping("/service-categories")
    public ResponseEntity<?> getServiceCategories() {
        logger.info("Received get request to fetch all ServiceCategories");
        try {
            List<ServiceCategoryResponse> serviceCategoryResponses = serviceCategoryService.getServiceCategories();
            return ResponseEntity.ok(serviceCategoryResponses);
        } catch (Exception e) {
            logger.error("An error occurred while processing the request", e);
            return ResponseEntity.internalServerError().body("Something went wrong");
        }
    }

    @GetMapping("/service-categories/{serviceCategoryId}/main-services")
    public ResponseEntity<?> getAllMainServicesByServiceCategoryId(@PathVariable Long serviceCategoryId) {
        logger.info("Received get request to fetch all MainServices related to serviceCategoryId: {}", serviceCategoryId);
        try {
            List<MainServiceResponse> mainServiceResponses = mainServiceService.getMainServicesByServiceCategoryId(serviceCategoryId);
            return ResponseEntity.ok(mainServiceResponses);
        } catch (MethodParamViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("An error occurred while processing the request", e);
            return ResponseEntity.internalServerError().body("Something went wrong");
        }
    }

    @GetMapping("/service-categories/{serviceCategoryId}/main-services/{mainServiceId}/sub-services")
    public ResponseEntity<?> getSubServicesByMainServiceId(@PathVariable Long serviceCategoryId, @PathVariable Long mainServiceId) {
        logger.info("Received get request to fetch all SubServices related to mainServiceId: {} and serviceCategoryId: {}", mainServiceId, serviceCategoryId);
        try {
            List<SubServiceResponse> subServiceResponses = subServiceService.getSubServicesByMainServiceId(serviceCategoryId, mainServiceId);
            return ResponseEntity.ok(subServiceResponses);
        } catch (MethodParamViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("An error occurred while processing the request", e);
            return ResponseEntity.internalServerError().body("Something went wrong");
        }
    }

}
