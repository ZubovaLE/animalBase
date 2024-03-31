package ru.interid.animalbase.service.animal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.interid.animalbase.model.Animal;
import ru.interid.animalbase.model.Dog;
import ru.interid.animalbase.model.dto.AnimalDto;
import ru.interid.animalbase.repository.DogRepository;
import ru.interid.animalbase.service.mapper.DogMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DogService implements AnimalService<Dog> {
    private final DogRepository dogRepository;
    private final DogMapper dogMapper;

    @Override
    public Animal.AnimalType getType() {
        return Animal.AnimalType.DOG;
    }

    @Override
    public Dog buildAnimal(AnimalDto dto) {
        return dogMapper.toEntity(dto);
    }

    @Override
    public void addAll(List<? extends Animal> animals) {
        dogRepository.saveAll((List<Dog>) animals);
    }

    @Override
    public List<Dog> getAll() {
        return dogRepository.findAll();
    }
}