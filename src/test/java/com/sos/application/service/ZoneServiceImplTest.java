package com.sos.application.service;

import com.sos.application.exception.ResourceNotExistsException;
import com.sos.application.model.zone.response.StateResponse;
import com.sos.application.repository.ZoneRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ZoneServiceImplTest {

    @Mock
    private ZoneRepository zoneRepository;

    @InjectMocks
    private ZoneServiceImpl zoneService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @SneakyThrows
    public void test_get_states_valid() {
        List<String> states = new ArrayList<>();
        states.add("State1");
        states.add("State2");
        Mockito.when(zoneRepository.getStates()).thenReturn(states);

        StateResponse stateResponse = zoneService.getStates();

        assertEquals(2,stateResponse.getStates().size(), "StateResponse should be having all the list of states, That's returned by zoneRepository.getStates()");
        Mockito.verify(zoneRepository, Mockito.times(1)).getStates();
   }

    @Test
    @SneakyThrows
    public void test_get_states_resourceNotExistsException() {
        List<String> states = new ArrayList<>();
        Mockito.when(zoneRepository.getStates()).thenReturn(states);

        assertThrows(ResourceNotExistsException.class, () -> zoneService.getStates());
        Mockito.verify(zoneRepository, Mockito.times(1)).getStates();
    }

}
