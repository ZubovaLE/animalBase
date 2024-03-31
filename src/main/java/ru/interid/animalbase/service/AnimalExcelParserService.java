package ru.interid.animalbase.service;

import org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.interid.animalbase.model.dto.AnimalDto;
import ru.interid.animalbase.model.Animal;
import ru.interid.animalbase.model.MassLoadAnimalData;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;

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
                AnimalDto dto = getAnimalDto(row);
                if (dto.haveError()) {
                    data.getErrorRows().add(i + 1);
                } else {
                    data.getAnimalDtos().add(dto);
                    data.getSuccessRows().add(i + 1);
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
        if (!checkColumnsValidity(rowOfHeaders)) {
            return "Некорректные столбцы";
        }
        String[] headersInDoc = getHeadersInDoc(rowOfHeaders);
        if (!Arrays.equals(HEADERS, headersInDoc)) {
            return "Неверные заголовки. Проверьте последовательность, название и тип заголовков";
        }
        return "";
    }

    private static boolean checkColumnsValidity(XSSFRow rowOfHeaders) {
        return rowOfHeaders.getLastCellNum() == HEADERS.length && rowOfHeaders.getPhysicalNumberOfCells() == HEADERS.length;
    }

    private static String[] getHeadersInDoc(XSSFRow rowOfHeaders) {
        String[] headersInDoc = new String[HEADERS.length];
        for (int i = 0; i < HEADERS.length; i++) {
            headersInDoc[i] = getStringCellValue(rowOfHeaders, i);
        }
        return headersInDoc;
    }

    private static AnimalDto getAnimalDto(XSSFRow row) {
        AnimalDto dto = new AnimalDto();
        dto.setAnimalType(Animal.AnimalType.getTypeByDescription(getStringCellValue(row, 0)));
        dto.setName(getStringCellValue(row, 1));
        dto.setSpeed(getNumericCellValue(row, 2));
        return dto;
    }

    private static String getStringCellValue(XSSFRow row, int cellNumber) {
        return getCellValue(row, cellNumber, CellType.STRING, Cell::getStringCellValue);
    }

    private static String getNumericCellValue(XSSFRow row, int cellNumber) {
        return getCellValue(row, cellNumber, CellType.NUMERIC, c -> String.valueOf(c.getNumericCellValue()));
    }

    private static String getCellValue(XSSFRow row, int cellNumber, CellType requiredCellType, Function<Cell, String> supplier) {
        Cell cell = row.getCell(cellNumber);
        if (cell != null) {
            return cell.getCellType().equals(requiredCellType) ? supplier.apply(cell) : null;
        }
        return null;
    }
}