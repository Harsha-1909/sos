package com.sos.application.model.zone;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@ToString

@Component
public class District {
    List<String> districts;
}
