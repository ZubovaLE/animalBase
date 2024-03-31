package ru.interid.animalbase.model;

import lombok.Getter;
import lombok.Setter;
import ru.interid.animalbase.model.dto.AnimalDto;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Getter
public class MassLoadAnimalData {
    private final List<AnimalDto> animalDtos = new ArrayList<>();
    private final List<Integer> errorRows = new ArrayList<>();
    private final List<Integer> successRows = new ArrayList<>();
    @Setter
    private String documentFormatErrorDescription;

    public String getDocumentErrorInfo() {
        StringJoiner sj = new StringJoiner(System.lineSeparator());
        if (isNotBlank(documentFormatErrorDescription)) {
            sj.add(documentFormatErrorDescription);
        }
        return sj.toString();
    }

    public String getSuccessRowsInfo() {
        StringJoiner sj = new StringJoiner(System.lineSeparator());
        sj.add("Успешно загружены следующие строки: " + successRows);
        return sj.toString();
    }

    public String getErrorRowsInfo() {
        StringJoiner sj = new StringJoiner(System.lineSeparator());
        if (!errorRows.isEmpty()) {
            sj.add("Проверьте корректность введенных данных в следующих строках: " + errorRows);
        }
        return sj.toString();
    }
}