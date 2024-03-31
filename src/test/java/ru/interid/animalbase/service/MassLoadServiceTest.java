package ru.interid.animalbase.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.interid.animalbase.model.Animal;
import ru.interid.animalbase.model.dto.AnimalDto;

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
        //Given
        AnimalDto birdDto = buildDto(Animal.AnimalType.BIRD, "bird", "123");
        AnimalDto dogDto = buildDto(Animal.AnimalType.DOG, "dog", "321");

        //When
        massLoadService.addAll(List.of(birdDto, dogDto));

        //Then
        verify(dogService, times(1)).addAll(anyList());
        verify(dogService, times(1)).buildAnimal(dogDto);
        verify(birdService, times(1)).addAll(anyList());
        verify(birdService, times(1)).buildAnimal(birdDto);
        verifyNoMoreInteractions(dogService, birdService);
    }

    @Test
    void whenAddAllThenCallDogServices() {
        //Given
        AnimalDto dogDto = buildDto(Animal.AnimalType.DOG, "dog", "321");

        //When
        massLoadService.addAll(List.of(dogDto));

        //Then
        verify(dogService, times(1)).addAll(anyList());
        verify(dogService, times(1)).buildAnimal(dogDto);
//        verifyNoInteractions(birdService);
        verifyNoMoreInteractions(dogService);
    }

    @Test
    void whenAddAllThenCallBirdServices() {
        //Given
        AnimalDto birdDto = buildDto(Animal.AnimalType.BIRD, "bird", "123");

        //When
        massLoadService.addAll(List.of(birdDto));

        //Then
        verify(birdService, times(1)).addAll(anyList());
        verify(birdService, times(1)).buildAnimal(birdDto);
//        verifyNoInteractions(dogService);
        verifyNoMoreInteractions(birdService);
    }

    private AnimalDto buildDto(Animal.AnimalType type, String name, String speed) {
        AnimalDto dto = new AnimalDto();
        dto.setAnimalType(type);
        dto.setName(name);
        dto.setSpeed(speed);
        return dto;
    }

}