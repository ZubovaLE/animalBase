package ru.interid.animalbase.mapper;

import org.springframework.stereotype.Service;
import ru.interid.animalbase.model.Animal;

import java.util.List;
import java.util.Map;

@Service
public class MassLoadService {
    public void addAll(Map<Animal.AnimalType, List<? extends Animal>> animals) {

    }
}
