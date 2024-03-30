package ru.interid.animalbase.mapper;

import org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.interid.animalbase.mapper.dto.AnimalDto;
import ru.interid.animalbase.model.Animal;
import ru.interid.animalbase.model.MassLoadAnimalData;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
public class AnimalExcelParserService {
    public static final String[] HEADERS = {"Тип", "Имя", "Параметр"};
    public static final int REQUIRED_NUMBER_OF_SHEETS = 1;

    public MassLoadAnimalData parseExcelDocument(MultipartFile file) {
        MassLoadAnimalData data = new MassLoadAnimalData();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(new ByteArrayInputStream(file.getBytes()));
            if (workbook.getNumberOfSheets() != REQUIRED_NUMBER_OF_SHEETS) {
                data.setDocumentFormatErrorDescription("Превышено требуемое количество листов в документе.");
                return data;
            }
            XSSFSheet sheet = workbook.getSheetAt(0);
            String errorDescription = checkTableHeaderValidity(sheet.getRow(0));
            if (isNotBlank(errorDescription)) {
                data.setDocumentFormatErrorDescription(errorDescription);
                return data;
            }

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                XSSFRow row = sheet.getRow(i);
                AnimalDto dto = new AnimalDto();
                //todo если такого параметра нет, проверить что вернёт
                dto.setAnimalType(Animal.AnimalType.getTypeByDescription(row.getCell(0).getStringCellValue()));
                dto.setName(row.getCell(1).getStringCellValue());
                dto.setSpeed(String.valueOf(row.getCell(2).getNumericCellValue()));
                if (dto.haveError()) {
                    data.getErrorRows().add(i + 1);
                } else {
                    data.getAnimalDtos().add(dto);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotOfficeXmlFileException e) {
            data.setDocumentFormatErrorDescription("Некорректный формат файла");
        }
        return data;
    }

    public static String checkTableHeaderValidity(XSSFRow rowOfHeaders) {
        if (rowOfHeaders.getLastCellNum() != HEADERS.length || rowOfHeaders.getPhysicalNumberOfCells() != HEADERS.length) {
            return "Некорректные столбцы";
        }
        String[] headersInDoc = new String[HEADERS.length];
        for (int i = 0; i < HEADERS.length; i++) {
            headersInDoc[i] = rowOfHeaders.getCell(i).getStringCellValue();
        }

        if (!Arrays.equals(HEADERS, headersInDoc)) {
            return "Неверные заголовки. Проверьте последовательность и/или названия заголовков";
        }
        return "";
    }
}