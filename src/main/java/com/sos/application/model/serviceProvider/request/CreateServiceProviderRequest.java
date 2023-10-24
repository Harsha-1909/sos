package com.sos.application.model.serviceProvider.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateServiceProviderRequest {

    private String name;

    private String phoneNumber;

}
