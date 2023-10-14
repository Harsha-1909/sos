package com.sos.application.service;

import com.sos.application.model.zone.Area;
import com.sos.application.model.zone.District;
import com.sos.application.model.zone.State;
import com.sos.application.model.zone.SubDistrict;
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

    public District getDistricts(String state){
        List<String> districts = zoneRepository.getDistricts(state);
        district.setDistricts(districts);
        return district;
    }

    public SubDistrict getSubDistricts(String state, String district){
        List<String> subDistricts = zoneRepository.getSubDistricts(state, district);
        subDistrict.setSubDistricts(subDistricts);
        return subDistrict;
    }

    public Area getAreas(String state, String district, String subDistrict){
        List<String> areas = zoneRepository.getAreas(state, district, subDistrict);
        area.setAreas(areas);
        return area;
    }

}
