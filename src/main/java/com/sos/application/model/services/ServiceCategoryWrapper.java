package com.sos.application.model.services;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Component
public class ServiceCategoryWrapper {
    Long id;
    String name;
}
