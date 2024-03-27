package ru.interid.animalbase.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.interid.animalbase.model.Animal;
import ru.interid.animalbase.model.Dog;
import ru.interid.animalbase.repository.DogRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DogService implements AnimalService<Dog> {
    private final DogRepository dogRepository;

    @Override
    public void addAll(List<Dog> animals) {
        dogRepository.saveAll(animals);
    }

    @Override
    public List<Dog> getAll() {
        return dogRepository.findAll();
    }

    @Override
    public Animal.AnimalType getType() {
        return Animal.AnimalType.DOG;
    }
}