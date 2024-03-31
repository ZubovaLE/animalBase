package ru.interid.animalbase.service.animal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.interid.animalbase.model.Animal;
import ru.interid.animalbase.model.Bird;
import ru.interid.animalbase.model.dto.AnimalDto;
import ru.interid.animalbase.repository.BirdRepository;
import ru.interid.animalbase.service.mapper.BirdMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BirdService implements AnimalService<Bird> {
    private final BirdRepository birdRepository;
    private final BirdMapper birdMapper;

    @Override
    public Animal.AnimalType getType() {
        return Animal.AnimalType.BIRD;
    }

    @Override
    public Bird buildAnimal(AnimalDto dto) {
        return birdMapper.toEntity(dto);
    }

    @Override
    public void addAll(List<? extends Animal> animals) {
        birdRepository.saveAll((List<Bird>) animals);
    }

    @Override
    public List<Bird> getAll() {
        return birdRepository.findAll();
    }
}