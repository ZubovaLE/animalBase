package ru.interid.animalbase.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.interid.animalbase.model.Animal;
import ru.interid.animalbase.model.Dog;
import ru.interid.animalbase.model.dto.AnimalDto;
import ru.interid.animalbase.repository.DogRepository;
import ru.interid.animalbase.service.animal.DogService;
import ru.interid.animalbase.service.mapper.DogMapper;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DogServiceTest {
    @InjectMocks
    private DogService dogService;

    @Mock
    private DogRepository dogRepository;

    @Mock
    private DogMapper dogMapper;

    @Test
    void whenGetTypeThenReturnDogType() {
        // Given

        // When
        Animal.AnimalType type = dogService.getType();

        // Then
        assertEquals(Animal.AnimalType.DOG, type);
    }

    @Test
    void whenBuildAnimalThenCallDogMapper() {
        // Given
        AnimalDto dto = new AnimalDto();

        // When
        dogService.buildAnimal(dto);

        // Then
        verify(dogMapper, times(1)).toEntity(dto);
        verifyNoMoreInteractions(dogMapper);
        verifyNoMoreInteractions(dogRepository);
    }

    @Test
    void whenAddAllThenCallDogRepository() {
        // Given
        List<Dog> dogs = Collections.emptyList();

        // When
        dogService.addAll(dogs);

        // Then
        verify(dogRepository, times(1)).saveAll(dogs);
        verifyNoMoreInteractions(dogRepository);
        verifyNoMoreInteractions(dogMapper);
    }

    @Test
    void whenGetAllThenCallDogRepository() {
        // Given
        Dog dog = new Dog(1L, "dog", "123");
        when(dogRepository.findAll()).thenReturn(List.of(dog));

        // When
        List<Dog> dogs = dogService.getAll();

        // Then
        verify(dogRepository, times(1)).findAll();
        assertEquals(1, dogs.size());
        assertEquals(dog.getName(), dogs.get(0).getName());
        assertEquals(dog.getRunSpeed(), dogs.get(0).getRunSpeed());
        verifyNoMoreInteractions(dogRepository);
        verifyNoInteractions(dogMapper);

    }
}