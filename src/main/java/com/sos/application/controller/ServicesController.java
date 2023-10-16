package com.sos.application.controller;

import com.sos.application.entity.ServiceCategory;
import com.sos.application.model.services.ServiceCategoryWrapper;
import com.sos.application.service.MainServiceService;
import com.sos.application.service.ServiceCategoryService;
import com.sos.application.service.SubServiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ServicesController {
    private final Logger logger = LoggerFactory.getLogger(ServicesController.class);
    @Autowired
    private ServiceCategoryService serviceCategoryService;
    @Autowired
    private MainServiceService mainServiceService;
    @Autowired
    private SubServiceService subServiceService;

    @GetMapping("/service-categories")
    public ResponseEntity<?> getServiceCategories(){
        logger.info("Received get request to fetch all ServiceCategories");
        try {
            List<ServiceCategory> serviceCategories = serviceCategoryService.findAllServiceCategory();
            List<ServiceCategoryWrapper> serviceCategoryWrappers = new ArrayList<>();
            for (ServiceCategory serviceCategory : serviceCategories) {
                serviceCategoryWrappers.add(new ServiceCategoryWrapper(serviceCategory.getId(), serviceCategory.getName()));
            }
            return ResponseEntity.ok(serviceCategoryWrappers);
        } catch (Exception e) {
            logger.error("An error occurred while processing the request", e);
            return ResponseEntity.internalServerError().body("Something went wrong");
        }
    }

}
