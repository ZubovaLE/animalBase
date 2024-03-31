package ru.interid.animalbase.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.interid.animalbase.model.Animal;
import ru.interid.animalbase.model.Bird;
import ru.interid.animalbase.model.dto.AnimalDto;
import ru.interid.animalbase.repository.BirdRepository;
import ru.interid.animalbase.service.animal.BirdService;
import ru.interid.animalbase.service.mapper.BirdMapper;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BirdServiceTest {
    @InjectMocks
    private BirdService birdService;

    @Mock
    private BirdRepository birdRepository;

    @Mock
    private BirdMapper birdMapper;

    @Test
    void whenGetTypeThenReturnBirdType() {
        //Given
        //When
        Animal.AnimalType type = birdService.getType();

        //Then
        assertEquals(Animal.AnimalType.BIRD, type);
    }

    @Test
    void whenBuildAnimalThenCallBirdMapper() {
        //Given
        AnimalDto dto = new AnimalDto();

        //When
        birdService.buildAnimal(dto);

        //Then
        verify(birdMapper, times(1)).toEntity(dto);
        verifyNoMoreInteractions(birdMapper);
        verifyNoInteractions(birdRepository);
    }

    @Test
    void whenAddAllThenCallBirdRepository() {
        //Given
        List<Bird> birds = Collections.emptyList();

        //When
        birdService.addAll(birds);

        //Then
        verify(birdRepository, times(1)).saveAll(birds);
        verifyNoMoreInteractions(birdRepository);
        verifyNoInteractions(birdMapper);
    }

    @Test
    void whenGetAllThenCallBirdRepository() {
        //Given
        Bird bird = new Bird(1L, "bird", BigDecimal.TEN);

        when(birdRepository.findAll()).thenReturn(List.of(bird));

        //When
        List<Bird> birds = birdService.getAll();

        //Then
        verify(birdRepository, times(1)).findAll();
        assertEquals(1, birds.size());
        assertEquals(bird.getName(), birds.get(0).getName());
        assertEquals(bird.getFlightSpeed(), birds.get(0).getFlightSpeed());
        verifyNoMoreInteractions(birdRepository);
        verifyNoInteractions(birdMapper);
    }


}