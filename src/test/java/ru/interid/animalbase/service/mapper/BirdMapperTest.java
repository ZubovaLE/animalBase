package ru.interid.animalbase.service.mapper;

import org.junit.jupiter.api.Test;
import ru.interid.animalbase.model.Animal;
import ru.interid.animalbase.model.Bird;
import ru.interid.animalbase.model.dto.AnimalDto;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class BirdMapperTest {
    private final BirdMapper birdMapper = new BirdMapperImpl();

    @Test
    void whenValidDtoThenReturnValidBirdObject() {
        //Given
        AnimalDto dto = new AnimalDto();
        dto.setAnimalType(Animal.AnimalType.BIRD);
        dto.setName("bird_name");
        dto.setSpeed("123");

        //When
        Bird bird = birdMapper.toEntity(dto);

        //Then
        assertEquals(dto.getName(), bird.getName());
        assertEquals(new BigDecimal(dto.getSpeed()), bird.getFlightSpeed());
    }

    @Test
    void whenEmptyDtoThenReturnEmptyBirdObject() {
        //Given
        AnimalDto dto = new AnimalDto();

        //When
        Bird bird = birdMapper.toEntity(dto);

        //Then
        assertNull(bird.getName());
        assertNull(bird.getFlightSpeed());
    }
}