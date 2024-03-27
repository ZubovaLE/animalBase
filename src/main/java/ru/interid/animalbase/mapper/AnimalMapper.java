package ru.interid.animalbase.mapper;

import ru.interid.animalbase.mapper.dto.AnimalDto;
import ru.interid.animalbase.model.Animal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnimalMapper {
    public Map<Animal.AnimalType, List<? extends Animal>> getAnimals(List<AnimalDto> animalDtos) {
        Map<Animal.AnimalType, List<? extends Animal>> animals = new HashMap<>();
        // logic
        return animals;
    }
}
