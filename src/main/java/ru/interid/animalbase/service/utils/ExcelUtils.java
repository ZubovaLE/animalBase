package ru.interid.animalbase.service.utils;

import lombok.experimental.UtilityClass;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;

import java.util.Arrays;

@UtilityClass
public class ExcelUtils {

    public static String checkTableHeaderValidity(XSSFRow rowOfHeaders, String[] expectedHeaders) {
        if (rowOfHeaders == null) {
            return "Необходимо заполнить названия колонок";
        }
        if (!checkColumnsValidity(rowOfHeaders, getHeadersAmount(expectedHeaders))) {
            return "Некорректные столбцы";
        }
        String[] headersInDoc = getHeadersInDoc(rowOfHeaders, getHeadersAmount(expectedHeaders));
        if (!Arrays.equals(expectedHeaders, headersInDoc)) {
            return "Неверные заголовки. Проверьте последовательность, название и тип заголовков";
        }
        return "";
    }

    public static String getCellValue(XSSFRow row, int cellNumber) {
        XSSFCell cell = row.getCell(cellNumber);
        if (cell != null) {
            if (cell.getCellType() != CellType.STRING) {
                cell.setCellType(CellType.STRING);
            }
            return cell.getStringCellValue();
        }
        return "";
    }

    private static int getHeadersAmount(String[] expectedHeaders) {
        return expectedHeaders != null ? expectedHeaders.length : 0;
    }

    private static boolean checkColumnsValidity(XSSFRow rowOfHeaders, int headersAmount) {
        return rowOfHeaders.getLastCellNum() == headersAmount && rowOfHeaders.getPhysicalNumberOfCells() == headersAmount;
    }

    private static String[] getHeadersInDoc(XSSFRow rowOfHeaders, int headersAmount) {
        String[] headersInDoc = new String[headersAmount];
        for (int i = 0; i < headersAmount; i++) {
            headersInDoc[i] = getCellValue(rowOfHeaders, i);
        }
        return headersInDoc;
    }
}
