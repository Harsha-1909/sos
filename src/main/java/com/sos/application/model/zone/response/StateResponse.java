package com.sos.application.model.zone.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class StateResponse {
    private List<String> states;
}
