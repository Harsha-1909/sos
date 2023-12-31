package com.sos.application.controller;

import com.sos.application.entity.Zone;
import com.sos.application.model.zone.Area;
import com.sos.application.model.zone.District;
import com.sos.application.model.zone.State;
import com.sos.application.model.zone.SubDistrict;
import com.sos.application.repository.ZoneRepository;
import com.sos.application.service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class ZoneController {

    @Autowired
    private ZoneRepository zoneRepository;

    @Autowired
    private ZoneService zoneService;

    @GetMapping("/zones/{zoneId}")
    public ResponseEntity<Optional<Zone>> getZone(@PathVariable Long zoneId){
        Optional<Zone> zone = zoneRepository.findById(zoneId);
        if(zone.isPresent())
            return ResponseEntity.ok().body(zone);
        return ResponseEntity.notFound().build();
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
