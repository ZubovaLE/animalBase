package ru.interid.animalbase.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.interid.animalbase.model.MassLoadAnimalData;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AnimalExcelParserServiceTest {
    private final static AnimalExcelParserService service = new AnimalExcelParserService();

    @Test
    void whenInvalidDocumentFormatThenReturnErrorMessage() throws IOException {

        // Given
        String fileName = "invalidFormat.txt";
        final MultipartFile multipartFile = new MockMultipartFile(fileName, this.getClass().getClassLoader()
                .getResourceAsStream(fileName));
        String expectedDocumentFormatErrorDescription = "Некорректный формат файла";

        // When
        MassLoadAnimalData data = service.parseExcelDocument(multipartFile);

        // Then
        assertEquals(expectedDocumentFormatErrorDescription, data.getDocumentFormatErrorDescription());
    }

    @ParameterizedTest
    @ValueSource(strings = {"invalidColumns_1.xlsx", "invalidColumns_2.xlsx", "invalidColumns_3.xlsx"})
    void whenInvalidColumnsThenReturnErrorMessage(String fileName) throws IOException {

        // Given
        MultipartFile multipartFile = new MockMultipartFile(fileName, this.getClass().getClassLoader()
                .getResourceAsStream(fileName));
        String expectedDocumentFormatErrorDescription = "Некорректные столбцы";

        // When
        MassLoadAnimalData data = service.parseExcelDocument(multipartFile);

        // Then
        assertEquals(expectedDocumentFormatErrorDescription, data.getDocumentFormatErrorDescription());
    }

    @Test
    void whenInvalidHeadersThenReturnErrorMessage() throws IOException {

        // Given
        final MultipartFile multipartFile = new MockMultipartFile("invalidHeaders.xlsx", this.getClass().getClassLoader()
                .getResourceAsStream("invalidHeaders.xlsx"));
        String expectedDocumentFormatErrorDescription = "Неверные заголовки. Проверьте последовательность, название и тип заголовков";

        // When
        MassLoadAnimalData data = service.parseExcelDocument(multipartFile);

        // Then
        assertEquals(expectedDocumentFormatErrorDescription, data.getDocumentFormatErrorDescription());
    }

    @Test
    void whenEmptyHeaderThenReturnErrorMessage() throws IOException {

        // Given
        final MultipartFile multipartFile = new MockMultipartFile("emptyHeader.xlsx", this.getClass().getClassLoader()
                .getResourceAsStream("emptyHeader.xlsx"));
        String expectedDocumentFormatErrorDescription = "Необходимо заполнить заголовки столбцов";

        // When
        MassLoadAnimalData data = service.parseExcelDocument(multipartFile);

        // Then
        assertEquals(expectedDocumentFormatErrorDescription, data.getDocumentFormatErrorDescription());
    }

    @ParameterizedTest
    @ValueSource(strings = {"validDocument_1.xlsx", "validDocument_2.xlsx"})
    void whenValidDocumentThenReturnSuccessRows(String fileName) throws IOException {

        // Given
        final MultipartFile multipartFile = new MockMultipartFile(fileName, this.getClass().getClassLoader()
                .getResourceAsStream(fileName));
        int expectedSizeOfSuccessRows = 2;
        List<Integer> expectedSuccessRows = List.of(2, 3);
        String expectedName = "Test Dog";

        // When
        MassLoadAnimalData data = service.parseExcelDocument(multipartFile);

        // Then
        assertTrue(data.getErrorRows().isEmpty());
        assertEquals(expectedSizeOfSuccessRows, data.getSuccessRows().size());
        assertEquals(expectedSizeOfSuccessRows, data.getAnimalDtos().size());
        assertIterableEquals(expectedSuccessRows, data.getSuccessRows());
        assertEquals(expectedName, data.getAnimalDtos().get(0).getName());
    }

    @ParameterizedTest
    @ValueSource(strings = {"validAndInvalidCells.xlsx", "emptyRow.xlsx"})
    void whenValidAndInvalidCellsThenReturnSuccessRowsAndErrorRows(String fileName) throws IOException {

        // Given
        final MultipartFile multipartFile = new MockMultipartFile(fileName,
                this.getClass().getClassLoader().getResourceAsStream(fileName));

        int expectedSizeOfSuccessRows = 2;
        int expectedSizeOfErrorsRows = 1;

        List<Integer> expectedSuccessRows = List.of(2, 4);
        List<Integer> expectedErrorsRows = List.of(3);

        // When
        MassLoadAnimalData data = service.parseExcelDocument(multipartFile);

        // Then
        assertEquals(expectedSizeOfSuccessRows, data.getSuccessRows().size());
        assertEquals(expectedSizeOfSuccessRows, data.getAnimalDtos().size());
        assertIterableEquals(expectedSuccessRows, data.getSuccessRows());
        assertEquals(expectedSizeOfErrorsRows, data.getErrorRows().size());
        assertIterableEquals(expectedErrorsRows, data.getErrorRows());
    }
}