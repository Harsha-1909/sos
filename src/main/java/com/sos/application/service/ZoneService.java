package com.sos.application.service;

import com.sos.application.model.Area;
import com.sos.application.model.District;
import com.sos.application.model.State;
import com.sos.application.model.SubDistrict;
import com.sos.application.repository.ZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class ZoneService {
    @Autowired
    private ZoneRepository zoneRepository;

    @Autowired
    private State state;

    @Autowired
    private District district;

    @Autowired
    private SubDistrict subDistrict;

    @Autowired
    private Area area;

    public Optional<State> getStates(){
        List<String> states = zoneRepository.getStates();
        state.setStates(states);
        return Optional.of(state);
    }

    public Optional<District> getDistricts(String state){
        Optional<List<String>> districts = zoneRepository.getDistricts(state);
        district.setDistricts(districts.get());
        return Optional.of(district);
    }

    public Optional<SubDistrict> getSubDistricts(String state, String district){
        Optional<List<String>> subDistricts = zoneRepository.getSubDistricts(state, district);
        subDistrict.setSubDistricts(subDistricts.get());
        return Optional.of(subDistrict);
    }

    public Optional<Area> getAreas(String state, String district, String subDistrict){
        Optional<List<String>> areas = zoneRepository.getAreas(state, district, subDistrict);
        area.setAreas(areas.get());
        return Optional.of(area);
    }

}
