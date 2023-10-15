package com.sos.application.admin.controller;

import com.sos.application.entity.ServiceCategory;
import com.sos.application.exception.MethodParamViolationException;
import com.sos.application.repository.ServiceCategoryRepository;

import com.sos.application.service.ServiceCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class ServiceCategoryController {

    private static final Logger logger = LoggerFactory.getLogger(ServiceCategoryController.class);
    @Autowired
    private ServiceCategoryService serviceCategoryService;
    @Autowired
    private ServiceCategoryRepository serviceCategoryRepository;

    @PostMapping("/service-categories")
    public ResponseEntity<?> createServiceCategory(@RequestBody ServiceCategory serviceCategory) {
        logger.info("Received post request for creating serviceCategory with RequestBody: {}",serviceCategory);

        try {
            serviceCategoryService.createServiceCategory(serviceCategory);
            return ResponseEntity.status(HttpStatus.CREATED).body("successfully created service category");
        } catch (MethodParamViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/service-categories/{serviceCategoryId}")
    public ResponseEntity<?>  deleteServiceCategory(@PathVariable Long serviceCategoryId){
        logger.info("Received delete request for serviceCategory id: {}",serviceCategoryId);

        Optional<ServiceCategory> result = serviceCategoryRepository.findById(serviceCategoryId);
        if(result.isPresent()){
            serviceCategoryRepository.deleteById(serviceCategoryId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid service category");
    }

}
