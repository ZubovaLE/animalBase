package ru.interid.animalbase.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.interid.animalbase.model.Animal;
import ru.interid.animalbase.model.Bird;
import ru.interid.animalbase.repository.BirdRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BirdService implements AnimalService<Bird> {
    private final BirdRepository birdRepository;

    @Override
    public void addAll(List<Bird> animals) {
        birdRepository.saveAll(animals);
    }

    @Override
    public Animal.AnimalType getType() {
        return Animal.AnimalType.BIRD;
    }

    @Override
    public List<Bird> getAll() {
        return birdRepository.findAll();
    }
}
