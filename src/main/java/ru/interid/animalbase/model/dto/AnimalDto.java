package ru.interid.animalbase.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ru.interid.animalbase.model.Animal;

import static org.apache.commons.lang3.StringUtils.isAnyBlank;

@Getter
@Setter
@EqualsAndHashCode
public class AnimalDto {
    private Animal.AnimalType animalType;
    private String name;
    private String speed;

    public boolean haveError() {
        return animalType == null || isAnyBlank(name, speed);
    }
}