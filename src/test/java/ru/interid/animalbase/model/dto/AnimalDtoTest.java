package ru.interid.animalbase.model.dto;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.interid.animalbase.model.Animal;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AnimalDtoTest {

    @ParameterizedTest
    @CsvSource(value = {"null, bird, 123",
            "BIRD, null, 123",
            "DOG, bird, null"},
            nullValues = "null")
    void whenAnimalTypeIsNullThenReturnTrue(String type, String name, String speed) {

        // Given
        AnimalDto dto = buildDto(type == null ? null : Animal.AnimalType.valueOf(type), name, speed);

        // When

        // Then
        assertTrue(dto.haveError());
    }

    private AnimalDto buildDto(Animal.AnimalType type, String name, String speed) {
        AnimalDto dto = new AnimalDto();
        dto.setAnimalType(type);
        dto.setName(name);
        dto.setSpeed(speed);
        return dto;
    }
}