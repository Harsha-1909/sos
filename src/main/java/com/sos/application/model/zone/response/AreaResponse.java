package com.sos.application.model.zone.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AreaResponse {
    private List<String> areas;
}
