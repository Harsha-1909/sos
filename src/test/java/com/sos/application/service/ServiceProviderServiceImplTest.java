package com.sos.application.service;

import com.sos.application.entity.ServiceProvider;
import com.sos.application.exception.BadRequestBodyException;
import com.sos.application.model.serviceProvider.request.CreateServiceProviderRequest;
import com.sos.application.model.serviceProvider.response.CreateServiceProviderResponse;
import com.sos.application.repository.ServiceProviderRepository;
import com.sos.application.validator.ServiceProviderValidator;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;


class ServiceProviderServiceImplTest {

    @Mock
    private ServiceProviderRepository serviceProviderRepository;

    @Mock
    private ServiceProviderValidator serviceProviderValidator;

    @InjectMocks
    private ServiceProviderServiceImpl serviceProviderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @SneakyThrows
    void createAccountNameAndPhoneNumber_ValidRequest_Success() throws BadRequestBodyException {
        // Arrange
        CreateServiceProviderRequest request = new CreateServiceProviderRequest("John Doe", "1234567890");
        when(serviceProviderRepository.checkPhoneNumberExists(request.getPhoneNumber())).thenReturn(Optional.empty());
        ServiceProvider savedServiceProvider = createServiceProvider(request);
        Mockito.when(serviceProviderRepository.save(Mockito.any())).thenReturn(savedServiceProvider);

        // Act
        CreateServiceProviderResponse response = serviceProviderService.createAccountNameAndPhoneNumber(request);

        // Assert
        assertNotNull(response);
        assertEquals(savedServiceProvider.getId(), response.getId());
        assertEquals(request.getName(), response.getName());
        assertEquals(request.getPhoneNumber(), response.getPhoneNumber());
        Mockito.verify(serviceProviderValidator, times(1)).validateNameAndPhoneNumber(Mockito.any(), Mockito.any());
    }

    @Test
    @SneakyThrows
    void createAccountNameAndPhoneNumber_InValidRequest_ExceptionThrown() {
        CreateServiceProviderRequest request = new CreateServiceProviderRequest("dsd", "123456789T");
        doThrow(BadRequestBodyException.class).when(serviceProviderValidator).validateNameAndPhoneNumber(request.getName(), request.getPhoneNumber());

        assertThrows(BadRequestBodyException.class, () -> serviceProviderService.createAccountNameAndPhoneNumber(request));
        Mockito.verify(serviceProviderRepository, times(0)).checkPhoneNumberExists(Mockito.any());
    }

    @Test
    @SneakyThrows
    void createAccountNameAndPhoneNumber_ExistingPhoneNumber_ExceptionThrown() {
        CreateServiceProviderRequest request = new CreateServiceProviderRequest("John Doe", "1234567890");
        when(serviceProviderRepository.checkPhoneNumberExists(request.getPhoneNumber())).thenReturn(Optional.of(new ServiceProvider()));

        assertThrows(BadRequestBodyException.class, () -> serviceProviderService.createAccountNameAndPhoneNumber(request));
        Mockito.verify(serviceProviderValidator, times(1)).validateNameAndPhoneNumber(Mockito.any(), Mockito.any());
        Mockito.verify(serviceProviderRepository, times(0)).save(Mockito.any());
    }

    private ServiceProvider createServiceProvider(CreateServiceProviderRequest request) {
        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.setId(1L);
        serviceProvider.setName(request.getName());
        serviceProvider.setPhoneNumber(request.getPhoneNumber());
        serviceProvider.setHasVerified(false);
        serviceProvider.setCreatedTime(LocalDateTime.now());
        return serviceProvider;
    }

}
