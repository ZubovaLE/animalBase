package ru.interid.animalbase.service;

import org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.interid.animalbase.model.Animal;
import ru.interid.animalbase.model.MassLoadAnimalData;
import ru.interid.animalbase.model.dto.AnimalDto;
import ru.interid.animalbase.service.utils.ExcelUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
public class AnimalExcelParserService {
    public static final String[] HEADERS = {"Тип", "Имя", "Параметр"};
    public static final int REQUIRED_NUMBER_OF_SHEETS = 1;

    private static AnimalDto getAnimalDto(XSSFRow row) {
        AnimalDto dto = new AnimalDto();
        dto.setAnimalType(Animal.AnimalType.getTypeByDescription(ExcelUtils.getCellValue(row, 0)));
        dto.setName(ExcelUtils.getCellValue(row, 1));
        dto.setSpeed(ExcelUtils.getCellValue(row, 2));
        return dto;
    }

    public MassLoadAnimalData parseExcelDocument(MultipartFile file) {
        MassLoadAnimalData data = new MassLoadAnimalData();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(new ByteArrayInputStream(file.getBytes()));
            if (workbook.getNumberOfSheets() != REQUIRED_NUMBER_OF_SHEETS) {
                data.setDocumentFormatErrorDescription("Превышено требуемое количество листов в документе.");
                return data;
            }
            XSSFSheet sheet = workbook.getSheetAt(0);
            String errorDescription = ExcelUtils.checkTableHeaderValidity(sheet.getRow(0), HEADERS);
            if (isNotBlank(errorDescription)) {
                data.setDocumentFormatErrorDescription(errorDescription);
                return data;
            }
            fillAnimalDataBySheet(data, sheet);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotOfficeXmlFileException e) {
            data.setDocumentFormatErrorDescription("Некорректный формат файла");
        }
        return data;
    }

    private void fillAnimalDataBySheet(MassLoadAnimalData data, XSSFSheet sheet) {
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            XSSFRow row = sheet.getRow(i);
            if (row == null) {
                data.getErrorRows().add(i + 1);
                continue;
            }
            AnimalDto dto = getAnimalDto(row);
            if (dto.haveError()) {
                data.getErrorRows().add(i + 1);
            } else {
                data.getAnimalDtos().add(dto);
                data.getSuccessRows().add(i + 1);
            }
        }
    }
}