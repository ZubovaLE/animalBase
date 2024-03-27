package ru.interid.animalbase.mapper.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.interid.animalbase.model.Animal;

@Getter
@Setter
@AllArgsConstructor
public class AnimalDto {
    private Animal.AnimalType animalType;
    private String name;
    private String speed;
}
