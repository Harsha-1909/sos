package com.sos.application.controller;

import com.sos.application.exception.ResourceNotExistsException;
import com.sos.application.model.zone.response.AreaResponse;
import com.sos.application.model.zone.response.DistrictResponse;
import com.sos.application.model.zone.response.StateResponse;
import com.sos.application.model.zone.response.SubDistrictResponse;
import com.sos.application.model.zone.response.ZoneResponse;
import com.sos.application.repository.ZoneRepository;
import com.sos.application.service.ZoneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ZoneController {
    private final static Logger logger = LoggerFactory.getLogger(ZoneController.class);
    @Autowired
    private ZoneRepository zoneRepository;

    @Autowired
    private ZoneService zoneService;

    @GetMapping("/zones/{zoneId}")
    public ResponseEntity<?> getZone(@PathVariable Long zoneId){
        logger.info("Received get request for Zone for zoneId: {}", zoneId);
        try {
            ZoneResponse zoneResponse = zoneService.getZone(zoneId);
            return ResponseEntity.ok().body(zoneResponse);
        } catch (ResourceNotExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            logger.error("Something went wrong", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/states")
    public ResponseEntity<?> getStates(){
        logger.info("Received get request for states");
        try {
            StateResponse stateResponse = zoneService.getStates();
            return ResponseEntity.ok().body(stateResponse);
        } catch (ResourceNotExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            logger.error("Something went wrong", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/states/{state}/districts")
    public ResponseEntity<?> getDistricts(@PathVariable String state){
        logger.info("Received get request for districts for state: {}", state);
        try {
            DistrictResponse districtResponse = zoneService.getDistricts(state);
            return ResponseEntity.ok().body(districtResponse);
        } catch (ResourceNotExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            logger.error("Something went wrong", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/states/{state}/districts/{district}/sub-districts")
    public ResponseEntity<?> getSubDistricts(@PathVariable String state, @PathVariable String district){
        logger.info("Received get request for subDistricts for state: {} and district: {}", state, district);
        try {
            SubDistrictResponse subDistrictResponse = zoneService.getSubDistricts(state, district);
            return ResponseEntity.ok().body(subDistrictResponse);
        } catch (ResourceNotExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            logger.error("Something went wrong", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/states/{state}/districts/{district}/sub-districts/{subDistrict}/areas")
    public ResponseEntity<?> getAreas(@PathVariable String state, @PathVariable String district, @PathVariable String subDistrict) {
        logger.info("Received get request for areas for state: {}, district: {} and subDistrict: {}", state, district, subDistrict);
        try {
            AreaResponse areaResponse = zoneService.getAreas(state, district, subDistrict);
            return ResponseEntity.ok().body(areaResponse);
        } catch (ResourceNotExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            logger.error("Something went wrong", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
