package ru.interid.animalbase.service.animal;

import ru.interid.animalbase.model.Animal;
import ru.interid.animalbase.model.dto.AnimalDto;

import java.util.List;

public interface AnimalService<T extends Animal> {
    Animal.AnimalType getType();

    T buildAnimal(AnimalDto dto);

    void addAll(List<? extends Animal> animals);

    List<T> getAll();

}