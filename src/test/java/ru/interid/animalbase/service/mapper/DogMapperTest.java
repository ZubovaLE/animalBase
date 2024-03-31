package ru.interid.animalbase.service.mapper;

import org.junit.jupiter.api.Test;
import ru.interid.animalbase.model.Animal;
import ru.interid.animalbase.model.Dog;
import ru.interid.animalbase.model.dto.AnimalDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class DogMapperTest {
    private final DogMapper dogMapper = new DogMapperImpl();

    @Test
    void whenValidDtoThenReturnValidBirdObject() {
        //Given
        AnimalDto dto = new AnimalDto();
        dto.setAnimalType(Animal.AnimalType.DOG);
        dto.setName("dog_name");
        dto.setSpeed("123");

        //When
        Dog dog = dogMapper.toEntity(dto);

        //Then
        assertEquals(dto.getName(), dog.getName());
        assertEquals(dto.getSpeed(), dog.getRunSpeed());
    }

    @Test
    void whenEmptyDtoThenReturnEmptyBirdObject() {
        //Given
        AnimalDto dto = new AnimalDto();

        //When
        Dog dog = dogMapper.toEntity(dto);

        //Then
        assertNull(dog.getName());
        assertNull(dog.getRunSpeed());
    }
}