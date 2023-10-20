package com.sos.application.validator;

import com.sos.application.exception.BadRequestBodyException;
import com.sos.application.model.zone.request.ZoneRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class ZoneValidatorImpl implements ZoneValidator {
    private final static Logger logger = LoggerFactory.getLogger(ZoneValidatorImpl.class);

    private final static List<String> keys = List.of("state", "district", "subDistrict", "area");

    @Override
    public void validateZoneRequestForFetchingZoneId(ZoneRequest zoneRequest) throws BadRequestBodyException {
        logger.info("Validating ZoneRequest: {}", zoneRequest);
        HashMap<String, String> hashMap = createHashMapWithZoneRequest(zoneRequest);
        logger.debug("created HashMap: {}", hashMap);

        for (String key: keys) {
            if (hashMap.get(key) == null) {
                String msg = String.format("%s should not be null", key);
                throw new  BadRequestBodyException(msg);
            } else if (hashMap.get(key).isEmpty()) {
                String msg = String.format("%s should not be empty", key);
                throw new  BadRequestBodyException(msg);
            }
        }
    }

    private HashMap<String, String> createHashMapWithZoneRequest(ZoneRequest zoneRequest) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(keys.get(0), zoneRequest.getState());
        hashMap.put(keys.get(1), zoneRequest.getDistrict());
        hashMap.put(keys.get(2), zoneRequest.getSubDistrict());
        hashMap.put(keys.get(3), zoneRequest.getArea());
        return hashMap;
    }
}
