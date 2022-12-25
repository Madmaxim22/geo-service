package ru.netology.i18n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import ru.netology.entity.Country;

class LocalizationServiceImplTest {

    @ParameterizedTest
    @EnumSource(
            value = Country.class,
            names = {"RUSSIA"})
    void localeRu(Country country) {
        LocalizationServiceImpl localizationService = new LocalizationServiceImpl();
        String preferences = localizationService.locale(country);

        Assertions.assertEquals("Добро пожаловать", preferences);
    }

    @ParameterizedTest
    @EnumSource(
            value = Country.class,
            names = {"RUSSIA"},
            mode = EnumSource.Mode.EXCLUDE)
    void localeOther(Country country) {
        LocalizationServiceImpl localizationService = new LocalizationServiceImpl();
        String preferences = localizationService.locale(country);

        Assertions.assertEquals("Welcome", preferences);
    }

}