package com.sos.application.model.zone.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZoneRequest {
    private String state;

    private String district;

    private String subDistrict;

    private String area;
}
