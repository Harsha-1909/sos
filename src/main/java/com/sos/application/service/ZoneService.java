package com.sos.application.service;

import com.sos.application.exception.ResourceNotExistsException;
import com.sos.application.model.zone.response.AreaResponse;
import com.sos.application.model.zone.response.DistrictResponse;
import com.sos.application.model.zone.response.StateResponse;
import com.sos.application.model.zone.response.SubDistrictResponse;
import com.sos.application.model.zone.response.ZoneResponse;

public interface ZoneService {
    ZoneResponse getZone(Long zoneId) throws ResourceNotExistsException;

    StateResponse getStates() throws ResourceNotExistsException;

    DistrictResponse getDistricts(String state) throws ResourceNotExistsException;

    SubDistrictResponse getSubDistricts(String state, String district) throws ResourceNotExistsException;

    AreaResponse getAreas(String state, String district, String subDistrict) throws ResourceNotExistsException;
}
