package ru.interid.animalbase.service;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Service;
import ru.interid.animalbase.mapper.dto.AnimalDto;
import ru.interid.animalbase.model.Bird;

import java.math.BigDecimal;

@Service
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface BirdMapper {

    @Mapping(target = "flightSpeed", expression = "java(transformSpeed(dto.getSpeed()))")
    Bird toEntity(AnimalDto dto);

    default BigDecimal transformSpeed(String speed) {
        return new BigDecimal(speed);
    }
}