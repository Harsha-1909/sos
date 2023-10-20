package com.sos.application.model.sosUser.response;

import com.sos.application.entity.SosUser;
import com.sos.application.model.zone.response.ZoneResponse;
import lombok.Data;

@Data
public class SosUserResponse {
    private Long id;

    private String name;

    private String phoneNumber;

    private ZoneResponse zoneResponse;

    public SosUserResponse(SosUser sosUser) {
        id = sosUser.getId();
        name = sosUser.getName();
        phoneNumber = sosUser.getPhoneNumber();
        zoneResponse = new ZoneResponse(sosUser.getZone());
    }
}
