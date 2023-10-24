package com.sos.application.service;

import com.sos.application.entity.ServiceCategory;
import com.sos.application.exception.BadRequestBodyException;
import com.sos.application.repository.ServiceCategoryRepository;
import com.sos.application.validator.ServiceCategoryValidator;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;

public class ServiceCategoryServiceImplTest {
    @Mock
    private ServiceCategoryRepository serviceCategoryRepository;

    @Mock
    private ServiceCategoryValidator serviceCategoryValidator;

    @InjectMocks
    private ServiceCategoryServiceImpl serviceCategoryService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @SneakyThrows
    public void test_createServiceCategory_when_serviceCategory_name_is_inValid() {
        ServiceCategory serviceCategory = new ServiceCategory();

        doThrow(new BadRequestBodyException("")).when(serviceCategoryValidator).validateServiceCategoryName(serviceCategory.getName());

        assertThrows(BadRequestBodyException.class, () -> serviceCategoryService.createServiceCategory(serviceCategory));
        Mockito.verify(serviceCategoryRepository, times(0)).save(Mockito.any());
    }

    @Test
    @SneakyThrows
    public void test_createServiceCategory_when_serviceCategory_name_exists() {
        ServiceCategory serviceCategory = new ServiceCategory();
        serviceCategory.setName("Plumber");

        Mockito.when(serviceCategoryRepository.findServiceCategoryByName(serviceCategory.getName())).thenReturn(Optional.of(serviceCategory));

        assertThrows(BadRequestBodyException.class, () -> serviceCategoryService.createServiceCategory(serviceCategory));
        Mockito.verify(serviceCategoryRepository, times(0)).save(Mockito.any());
    }

    @Test
    @SneakyThrows
    public void test_createServiceCategory_valid() {
        ServiceCategory serviceCategory = new ServiceCategory();
        serviceCategory.setName("Plumber");

        Mockito.when(serviceCategoryRepository.findServiceCategoryByName(serviceCategory.getName())).thenReturn(Optional.empty());
        Mockito.when(serviceCategoryRepository.save(serviceCategory)).thenReturn(serviceCategory);

        ServiceCategory createdServiceCategory = serviceCategoryService.createServiceCategory(serviceCategory);

        Mockito.verify(serviceCategoryValidator, times(1)).validateServiceCategoryName(serviceCategory.getName());
        Mockito.verify(serviceCategoryRepository, times(1)).save(Mockito.any());
        assertNotNull(createdServiceCategory);
    }

}
