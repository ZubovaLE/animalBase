package ru.interid.animalbase.model;

import lombok.Getter;
import lombok.Setter;
import ru.interid.animalbase.mapper.dto.AnimalDto;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Getter
public class MassLoadAnimalData {
    private final List<AnimalDto> animalDtos = new ArrayList<>();
    private final List<Integer> errorRows = new ArrayList<>();
    @Setter
    private String errorFormatDocumentDescription;

    public String getErrorInfo() {
        StringJoiner sj = new StringJoiner(System.lineSeparator());
        if (isNotBlank(errorFormatDocumentDescription)) {
            sj.add(errorFormatDocumentDescription);
        }
        if (!errorRows.isEmpty()) {
            sj.add("Проверьте корректность введенных данных в следующих строках: " + errorRows);
        }
        return sj.toString();
    }
}
