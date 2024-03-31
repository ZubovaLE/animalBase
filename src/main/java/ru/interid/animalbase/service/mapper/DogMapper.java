package ru.interid.animalbase.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.interid.animalbase.model.dto.AnimalDto;
import ru.interid.animalbase.model.Dog;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,  componentModel = "spring")
public interface DogMapper {

    @Mapping(target = "runSpeed", expression = "java(dto.getSpeed())")
    Dog toEntity(AnimalDto dto);
}