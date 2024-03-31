package ru.interid.animalbase.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.interid.animalbase.model.Animal;
import ru.interid.animalbase.model.dto.AnimalDto;
import ru.interid.animalbase.service.animal.BirdService;
import ru.interid.animalbase.service.animal.DogService;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MassLoadServiceTest {
    private MassLoadService massLoadService;

    @Mock
    private DogService dogService;

    @Mock
    private BirdService birdService;


    @BeforeEach
    void init() {
        when(dogService.getType()).thenReturn(Animal.AnimalType.DOG);
        when(birdService.getType()).thenReturn(Animal.AnimalType.BIRD);
        massLoadService = new MassLoadService(List.of(dogService, birdService));
    }

    @Test
    void whenAddAllThenCallDogAndBirdServices() {
        // Given
        AnimalDto birdDto = buildDto(Animal.AnimalType.BIRD, "bird", "123");
        AnimalDto dogDtoOne = buildDto(Animal.AnimalType.DOG, "dog one", "321");
        AnimalDto dogDtoTwo = buildDto(Animal.AnimalType.DOG, "dog two", "321");

        // When
        massLoadService.addAll(List.of(birdDto, dogDtoOne, dogDtoTwo));

        // Then
        verify(dogService, times(1)).addAll(anyList());
        verify(dogService, times(1)).buildAnimal(dogDtoOne);
        verify(dogService, times(1)).buildAnimal(dogDtoTwo);
        verify(birdService, times(1)).addAll(anyList());
        verify(birdService, times(1)).buildAnimal(birdDto);
        verifyNoMoreInteractions(dogService, birdService);
    }

    @Test
    void whenAddAllWithDogTypeThenCallDogServices() {
        // Given
        AnimalDto dogDto = buildDto(Animal.AnimalType.DOG, "dog", "321");

        // When
        massLoadService.addAll(List.of(dogDto));

        // Then
        verify(dogService, times(1)).addAll(anyList());
        verify(dogService, times(1)).buildAnimal(dogDto);
        verify(birdService, times(0)).addAll(any());
        verify(birdService, times(0)).buildAnimal(any());
        verifyNoMoreInteractions(dogService, birdService);
    }

    @Test
    void whenAddAllWithBirdTypeThenCallBirdServices() {

        // Given
        AnimalDto birdDto = buildDto(Animal.AnimalType.BIRD, "bird", "123");

        // When
        massLoadService.addAll(List.of(birdDto));

        // Then
        verify(birdService, times(1)).addAll(anyList());
        verify(birdService, times(1)).buildAnimal(birdDto);
        verify(dogService, times(0)).addAll(anyList());
        verify(dogService, times(0)).buildAnimal(any());
        verifyNoMoreInteractions(birdService, dogService);
    }

    private AnimalDto buildDto(Animal.AnimalType type, String name, String speed) {
        AnimalDto dto = new AnimalDto();
        dto.setAnimalType(type);
        dto.setName(name);
        dto.setSpeed(speed);
        return dto;
    }

}