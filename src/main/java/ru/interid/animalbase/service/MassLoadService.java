package ru.interid.animalbase.service;

import org.springframework.stereotype.Service;
import ru.interid.animalbase.model.dto.AnimalDto;
import ru.interid.animalbase.model.Animal;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class MassLoadService {
    private final Map<Animal.AnimalType, AnimalService<? extends Animal>> services;

    public MassLoadService(List<AnimalService<? extends Animal>> services) {
        this.services = services.stream().collect(Collectors.toMap(AnimalService::getType, Function.identity()));
    }

    public void addAll(List<AnimalDto> animalDtosByType) {
        animalDtosByType.stream()
                .collect(Collectors.groupingBy(AnimalDto::getAnimalType))
                .forEach(
                        (type, dtos) -> services.get(type).addAll(
                                dtos.stream()
                                        .map(dto -> services.get(type).buildAnimal(dto))
                                        .toList()
                        )
                );
    }
}