package com.sos.application.model.zone;

import com.sos.application.entity.Zone;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ZoneResponse {
    private Long id;
    private String state;
    private String district;
    private String subDistrict;
    private String area;

    public ZoneResponse(Zone zone) {
        id = zone.getId();
        state = zone.getState();
        district = zone.getDistrict();
        subDistrict = zone.getSubDistrict();
        area = zone.getArea();
    }
}
