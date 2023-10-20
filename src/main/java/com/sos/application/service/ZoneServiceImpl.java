package com.sos.application.service;

import com.sos.application.entity.Zone;
import com.sos.application.exception.BadRequestBodyException;
import com.sos.application.exception.ResourceNotExistsException;
import com.sos.application.model.zone.request.ZoneRequest;
import com.sos.application.model.zone.response.AreaResponse;
import com.sos.application.model.zone.response.DistrictResponse;
import com.sos.application.model.zone.response.StateResponse;
import com.sos.application.model.zone.response.SubDistrictResponse;
import com.sos.application.model.zone.response.ZoneResponse;
import com.sos.application.repository.ZoneRepository;
import com.sos.application.validator.ZoneValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ZoneServiceImpl implements ZoneService {
    private static final Logger logger = LoggerFactory.getLogger(ZoneServiceImpl.class);
    @Autowired
    private ZoneRepository zoneRepository;

    @Autowired
    private ZoneValidator zoneValidator;

    public StateResponse getStates() throws ResourceNotExistsException {
        logger.info("Fetching states");
        List<String> states = zoneRepository.getStates();
        logger.debug("Fetched states list: {}", states);
        if (states.isEmpty()) {
            throw new ResourceNotExistsException("States not exists");
        }

        StateResponse stateResponse = new StateResponse();
        stateResponse.setStates(states);
        logger.debug("Constructed StateResponse: {}", stateResponse);

        return stateResponse;
    }

    public DistrictResponse getDistricts(String state) throws ResourceNotExistsException {
        logger.info("Fetching districts for state: {}",state);
        List<String> districts = zoneRepository.getDistricts(state);
        logger.debug("Fetched districts list: {}", districts);
        if (districts.isEmpty()) {
            throw new ResourceNotExistsException("Invalid request. check state name");
        }

        DistrictResponse districtResponse = new DistrictResponse();
        districtResponse.setDistricts(districts);
        logger.debug("Constructed DistrictResponse: {}", districtResponse);

        return districtResponse;
    }

    public SubDistrictResponse getSubDistricts(String state, String district) throws ResourceNotExistsException {
        logger.info("Fetching subDistricts for state: {} and district: {}", state, district);
        List<String> subDistricts = zoneRepository.getSubDistricts(state, district);
        logger.debug("Fetched subDistricts list: {}", subDistricts);
        if (subDistricts.isEmpty()) {
            throw new ResourceNotExistsException("Invalid request. check state and district names");
        }

        SubDistrictResponse subDistrictResponse = new SubDistrictResponse();
        subDistrictResponse.setSubDistricts(subDistricts);
        logger.debug("Constructed SubDistrictResponse: {}", subDistrictResponse);

        return subDistrictResponse;
    }

    public AreaResponse getAreas(String state, String district, String subDistrict) throws ResourceNotExistsException {
        logger.info("Fetching areas for state: {}, district: {} and subDistrict: {}", state, district, subDistrict);
        List<String> areas = zoneRepository.getAreas(state, district, subDistrict);
        logger.debug("Fetched areas list: {}", areas);
        if (areas.isEmpty()) {
            throw new ResourceNotExistsException("Invalid request. Check state, district and subDistrict names");
        }

        AreaResponse areaResponse = new AreaResponse();
        areaResponse.setAreas(areas);
        logger.debug("Constructed AreaResponse: {}", areaResponse);

        return areaResponse;
    }

    @Override
    public Zone getZone(ZoneRequest zoneRequest) throws BadRequestBodyException {
        logger.info("In getZone to fetch Zone for ZoneRequest: {}", zoneRequest);
        zoneValidator.validateZoneRequestForFetchingZoneId(zoneRequest);
        Zone zone = getZoneByZoneRequest(zoneRequest);
        logger.debug("Fetched Zone: {}", zone);
        return zone;
    }

    private Zone getZoneByZoneRequest(ZoneRequest zoneRequest) throws BadRequestBodyException {
        logger.info("Fetching Zone for ZoneRequest: {}", zoneRequest);
        Optional<Zone> zone = zoneRepository.getZone(
                zoneRequest.getState(),
                zoneRequest.getDistrict(),
                zoneRequest.getSubDistrict(),
                zoneRequest.getArea()
        );

        try {
            if (zone.isEmpty()) {
                throw new ResourceNotExistsException("Zone does not exists");
            }
            return zone.get();
        } catch (ResourceNotExistsException e) {
            throw new BadRequestBodyException(e.getMessage(), e);
        }
    }

    public ZoneResponse getZone(Long zoneId) throws ResourceNotExistsException {
        logger.info("Fetching Zone for zoneId: {}",zoneId);
        Optional<Zone> zone = zoneRepository.findById(zoneId);
        logger.debug("Fetched zone: {}",zone);
        if(zone.isEmpty()) {
            throw new ResourceNotExistsException("ZoneId is invalid");
        }
        else {
            return new ZoneResponse(zone.get());
        }
    }
}
