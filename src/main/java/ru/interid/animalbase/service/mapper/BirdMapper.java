package ru.interid.animalbase.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Service;
import ru.interid.animalbase.model.Bird;
import ru.interid.animalbase.model.dto.AnimalDto;

import java.math.BigDecimal;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface BirdMapper {

    @Mapping(target = "flightSpeed", expression = "java(transformSpeed(dto.getSpeed()))")
    Bird toEntity(AnimalDto dto);

    default BigDecimal transformSpeed(String speed) {
        return isNotBlank(speed) ? new BigDecimal(speed) : null;
    }
}