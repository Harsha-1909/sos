package com.sos.application.model.sosUser.request;

import com.sos.application.model.zone.request.ZoneRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSosUserRequest {
    private String name;

    private ZoneRequest zoneRequest;
}
