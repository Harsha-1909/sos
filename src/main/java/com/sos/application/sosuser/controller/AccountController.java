package com.sos.application.sosuser.controller;

import com.sos.application.entity.SosUser;
import com.sos.application.exception.BadRequestBodyException;
import com.sos.application.exception.MethodParamViolationException;
import com.sos.application.model.sosUser.request.UpdateSosUserRequest;
import com.sos.application.model.sosUser.response.SosUserResponse;
import com.sos.application.service.SosUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    private final static Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private SosUserService sosUserService;

    @PostMapping("/users")
    public ResponseEntity<?> createSosUser(@RequestBody SosUser sosUser) {
        logger.info("Received post request for creating new SosUser: {}", sosUser);
        try {
            sosUserService.createSosUser(sosUser);
            return ResponseEntity.status(HttpStatus.CREATED).body("Successfully created sosUser");
        } catch (BadRequestBodyException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("something went wrong", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<?> updateSosUser(@RequestBody UpdateSosUserRequest updateSosUserRequest, @PathVariable Long userId) {
        logger.info("Received put request for updating SosUser with UpdateSosUserRequest: {} and SosUserId: {}", updateSosUserRequest, userId);
        try {
            SosUserResponse sosUserResponse = sosUserService.updateSosUser(updateSosUserRequest, userId);
            return ResponseEntity.status(HttpStatus.OK).body(sosUserResponse);
        } catch (MethodParamViolationException | BadRequestBodyException e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("something went wrong", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<?> getSosUserById(@PathVariable Long userId) {
        logger.info("Received get request for fetching SosUser with sosUserId: {}", userId);
        try {
            SosUserResponse sosUserResponse = sosUserService.findBySosUserId(userId);
            return ResponseEntity.status(HttpStatus.OK).body(sosUserResponse);
        } catch (MethodParamViolationException e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("something went wrong", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }
}
