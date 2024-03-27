package ru.interid.animalbase.service;

import ru.interid.animalbase.model.Animal;

import java.util.List;

public interface AnimalService<T> {
    void addAll(List<T> animals);

    List<T> getAll();

    Animal.AnimalType getType();
}