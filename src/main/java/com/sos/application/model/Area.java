package com.sos.application.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor

@Component
public class Area {
    private List<String> areas;
}
