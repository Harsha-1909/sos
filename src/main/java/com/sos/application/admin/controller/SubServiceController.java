package com.sos.application.admin.controller;

import com.sos.application.entity.SubService;
import com.sos.application.exception.MethodParamViolationException;
import com.sos.application.service.SubServiceService;
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

@RestController
@RequestMapping("/admin/service-categories/{serviceCategoryId}/main-services/{mainServiceId}")
public class SubServiceController {
    private final static Logger logger = LoggerFactory.getLogger(SubServiceController.class);
    @Autowired
    private SubServiceService subServiceService;

    @PostMapping("/sub-services")
    public ResponseEntity<?> createSubService(@RequestBody SubService subService, @PathVariable Long serviceCategoryId, @PathVariable Long mainServiceId) {
        try {
            subServiceService.createSubService(serviceCategoryId, mainServiceId, subService);
            return ResponseEntity.status(HttpStatus.CREATED).body("Successfully created SubService");
        } catch (MethodParamViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/sub-services/{subServiceId}")
    public ResponseEntity<?> deleteSubService(@PathVariable Long serviceCategoryId, @PathVariable Long mainServiceId, @PathVariable Long subServiceId) {
        try {
            subServiceService.deleteSubService(serviceCategoryId, mainServiceId, subServiceId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully deleted SubService");
        } catch (MethodParamViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
