package ru.interid.animalbase.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.interid.animalbase.model.MassLoadAnimalData;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AnimalExcelParserServiceTest {
    private final static AnimalExcelParserService service = new AnimalExcelParserService();

    @Test
    void WhenInvalidDocumentFormatThenReturnErrorMessage() throws IOException {

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
    void WhenInvalidColumnsThenReturnErrorMessage(String fileName) throws IOException {

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
    void WhenInvalidHeadersThenReturnErrorMessage() throws IOException {

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
    void WhenValidDocumentThenReturnSuccessRows() throws IOException {

        // Given
        final MultipartFile multipartFile = new MockMultipartFile("validDocument.xlsx", this.getClass().getClassLoader()
                .getResourceAsStream("validDocument.xlsx"));
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

    @Test
    void WhenInvalidCellsThenReturnSuccessRowsAndErrorRows() throws IOException {

        // Given
        final MultipartFile multipartFile = new MockMultipartFile("validAndInvalidCells.xlsx",
                this.getClass().getClassLoader().getResourceAsStream("validAndInvalidCells.xlsx"));

        int expectedSizeOfSuccessRows = 1;
        int expectedSizeOfErrorsRows = 3;

        List<Integer> expectedSuccessRows = List.of(2);
        List<Integer> expectedErrorsRows = List.of(3, 4, 5);

        String expectedName = "Doggy";

        // When
        MassLoadAnimalData data = service.parseExcelDocument(multipartFile);

        // Then
        assertEquals(expectedSizeOfSuccessRows, data.getSuccessRows().size());
        assertEquals(expectedSizeOfSuccessRows, data.getAnimalDtos().size());
        assertIterableEquals(expectedSuccessRows, data.getSuccessRows());
        assertEquals(expectedSizeOfErrorsRows, data.getErrorRows().size());
        assertIterableEquals(expectedErrorsRows, data.getErrorRows());

        assertEquals(expectedName, data.getAnimalDtos().get(0).getName());
    }
}