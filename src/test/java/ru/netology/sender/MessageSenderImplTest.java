package ru.netology.sender;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;

import java.util.HashMap;
import java.util.Map;

class MessageSenderImplTest {
    GeoService geoService;
    LocalizationService localizationService;
    MessageSenderImpl messageSenderImpl;
    Map<String, String> headers = new HashMap<>();

    @BeforeEach
    void newmocks() {
        // arrange
        geoService = Mockito.mock(GeoService.class);
        localizationService = Mockito.mock(LocalizationService.class);
        messageSenderImpl = new MessageSenderImpl(geoService, localizationService);
    }

    @Test
    void sendRuTest() {
        // arrange
        Mockito.when(geoService.byIp("172.123.12.19")).thenReturn(new Location("Moscow", Country.RUSSIA, null, 0));
        Mockito.when(localizationService.locale(Country.RUSSIA)).thenReturn("Добро пожаловать");
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "172.123.12.19");
        // act
        String preferences = messageSenderImpl.send(headers);
        // assert
        Assertions.assertEquals("Добро пожаловать", preferences);
    }

    @Test
    void sendEuTest() {
        // arrange
        Mockito.when(geoService.byIp("96.123.12.19")).thenReturn(new Location("New York", Country.USA, null, 0));
        Mockito.when(localizationService.locale(Country.USA)).thenReturn("Welcome");
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "96.123.12.19");
        // act
        String preferences = messageSenderImpl.send(headers);
        // assert
        Assertions.assertEquals("Welcome", preferences);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void sendMethodNullAndEmptySourceTest(String argument) {
        //arrange
        Mockito.when(localizationService.locale(Country.USA)).thenReturn("Welcome");
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, argument);
        // act
        String preferences = messageSenderImpl.send(headers);
        // assert
        Assertions.assertEquals("Welcome", preferences);
    }
}