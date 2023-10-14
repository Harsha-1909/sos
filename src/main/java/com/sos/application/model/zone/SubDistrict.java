package com.sos.application.model.zone;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString

@Component
public class SubDistrict {
    private List<String> subDistricts;
}
