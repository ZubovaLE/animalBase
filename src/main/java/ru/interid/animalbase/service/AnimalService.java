package ru.interid.animalbase.service;

import ru.interid.animalbase.mapper.dto.AnimalDto;
import ru.interid.animalbase.model.Animal;

import java.util.List;

public interface AnimalService<T extends Animal> {
    Animal.AnimalType getType();

    T buildAnimal(AnimalDto dto);

    void addAll(List<? extends Animal> animals);

    List<T> getAll();

}