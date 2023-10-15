package com.sos.application.admin.controller;

import com.sos.application.entity.MainService;
import com.sos.application.exception.MethodParamViolationException;
import com.sos.application.repository.MainServiceRepository;
import com.sos.application.service.MainServiceService;
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

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/admin/service-categories/{serviceCategoryId}")
public class MainServiceController {

    private static final Logger logger = LoggerFactory.getLogger(MainServiceController.class);

    @Autowired
    private MainServiceService mainServiceService;

    @Autowired
    private MainServiceRepository mainServiceRepository;

    @PostMapping("/main-services")
    public ResponseEntity<?> createMainService(@RequestBody MainService mainService, @PathVariable Long serviceCategoryId) {
        logger.info("Received post request for creating mainService with RequestBody for mainService: {} and PathVariable for serviceCategoryId: {}", mainService, serviceCategoryId);

        try {
            mainServiceService.createMainService(mainService, serviceCategoryId);
            return ResponseEntity.status(HttpStatus.CREATED).body("successfully created main service");
        } catch (MethodParamViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/main-services/{mainServiceId}")
    public ResponseEntity<?> deleteMainService(@PathVariable Long serviceCategoryId, @PathVariable Long mainServiceId) {
        Optional<MainService> result = mainServiceRepository.findById(mainServiceId);
        if(result.isPresent()){
            if(!Objects.equals(result.get().getServiceCategory().getId(), serviceCategoryId)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect serviceCategoryId provided");
            }
            mainServiceRepository.deleteById(mainServiceId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect mainServiceId provided");
    }
}
