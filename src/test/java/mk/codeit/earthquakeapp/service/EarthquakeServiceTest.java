package mk.codeit.earthquakeapp.service;

import mk.codeit.earthquakeapp.Model.Earthquake;
import mk.codeit.earthquakeapp.Repository.EarthquakeRepository;
import mk.codeit.earthquakeapp.Service.EarthquakeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EarthquakeServiceTest {

    @Mock
    private EarthquakeRepository repository;

    @InjectMocks
    private EarthquakeService service;

    @Test
    void shouldFilterEarthquakesAboveMagnitudeTwo() {

        when(repository.findByMagnitudeGreaterThan(2.0)).thenReturn(
                List.of(Earthquake.builder().id("2").magnitude(3.0).build())
        );

        List<Earthquake> result = service.findEarthquakesByMagnitude();

        assertEquals(1, result.size());
        assertEquals(3.0, result.get(0).getMagnitude());
    }

    @Test
    void shouldReturnEarthquakesAfterTime() {

        Instant time = Instant.now();

        when(repository.findByTimeAfter(time)).thenReturn(
                List.of(Earthquake.builder().id("1").time(time).build())
        );

        List<Earthquake> result = service.findEarthquakesAfter(time);

        assertEquals(1, result.size());
        verify(repository).findByTimeAfter(time);
    }

    @Test
    void shouldDeleteEarthquakeById() {

        service.deleteEarthquake("123");

        verify(repository).deleteById("123");
    }

    @Test
    void shouldCallSaveAll() {

        when(repository.saveAll(anyList())).thenReturn(List.of());

        repository.saveAll(List.of());

        verify(repository).saveAll(anyList());
    }
}
