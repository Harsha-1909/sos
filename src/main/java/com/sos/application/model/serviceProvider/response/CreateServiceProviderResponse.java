package com.sos.application.model.serviceProvider.response;

import com.sos.application.entity.ServiceProvider;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateServiceProviderResponse {

    private Long id;

    private String name;

    private String phoneNumber;

}
