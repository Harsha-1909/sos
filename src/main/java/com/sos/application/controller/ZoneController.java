package com.sos.application.controller;

import com.sos.application.exception.ResourceNotExistsException;
import com.sos.application.model.zone.Area;
import com.sos.application.model.zone.District;
import com.sos.application.model.zone.State;
import com.sos.application.model.zone.SubDistrict;
import com.sos.application.model.zone.ZoneResponse;
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

import java.util.Optional;

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
    public ResponseEntity<Optional<State>> getStates(){
        Optional<State> states = zoneService.getStates();
        if(states.isPresent()){
            return ResponseEntity.ok().body(states);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/states/{state}/districts")
    public ResponseEntity<District> getDistricts(@PathVariable String state){
        District district = zoneService.getDistricts(state);
        if(district.getDistricts().isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(district);
    }

    @GetMapping("/states/{state}/districts/{district}/sub-districts")
    public ResponseEntity<SubDistrict> getSubDistricts(@PathVariable String state, @PathVariable String district){
        SubDistrict subDistrict = zoneService.getSubDistricts(state, district);
        if(subDistrict.getSubDistricts().isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(subDistrict);
    }

    @GetMapping("/states/{state}/districts/{district}/sub-districts/{subDistrict}/areas")
    public ResponseEntity<Area> getAreas(@PathVariable String state, @PathVariable String district, @PathVariable String subDistrict) {
        Area area = zoneService.getAreas(state, district, subDistrict);
        if (area.getAreas().isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(area);
    }

}
