package ru.netology.geo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class GeoServiceImplTest {

    GeoService geoService;

    private static Stream<Arguments> provideStringsForIsByIp() {
        return Stream.of(
                Arguments.of("127.0.0.1", new Location(null, null, null, 0)),
                Arguments.of("172.0.32.11", new Location("Moscow", Country.RUSSIA, "Lenina", 15)),
                Arguments.of("96.44.183.149", new Location("New York", Country.USA, " 10th Avenue", 32)),
                Arguments.of("172.1.1.1", new Location("Moscow", Country.RUSSIA, null, 0)),
                Arguments.of("96.1.1.1", new Location("New York", Country.USA, null, 0)),
                Arguments.of("168.1.1.1", null)
        );
    }

    @BeforeEach
    void newGeoService() {
        geoService = new GeoServiceImpl();
    }

    @ParameterizedTest
    @MethodSource("provideStringsForIsByIp")
    void byIpTest(String input, Location location) {
        // assert
        assertEquals(location, geoService.byIp(input));
    }

    @Test
    void byCoordinatesThrowsExceptionTest() {
        assertThrows(RuntimeException.class, () -> geoService.byCoordinates(3.11, 3.134));
    }

    @Test
    void byCoordinatesShouldThrowException() {
        Throwable exception = assertThrows(RuntimeException.class, () -> geoService.byCoordinates(3.11, 3.134));
        assertEquals("Not implemented", exception.getMessage());
    }
}