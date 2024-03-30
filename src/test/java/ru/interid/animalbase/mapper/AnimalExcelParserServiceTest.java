package ru.interid.animalbase.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.interid.animalbase.model.MassLoadAnimalData;

import java.io.IOException;

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
    @ValueSource(strings = {"invalidNumberOfRows_1.xlsx", "invalidNumberOfRows_2.xlsx"})
    void WhenInvalidRowsThenReturnErrorMessage(String fileName) throws IOException {

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
        String expectedDocumentFormatErrorDescription = "Неверные заголовки. Проверьте последовательность и/или названия заголовков";

        // When
        MassLoadAnimalData data = service.parseExcelDocument(multipartFile);

        // Then
        assertEquals(expectedDocumentFormatErrorDescription, data.getDocumentFormatErrorDescription());
    }

    @Test
    void checkTableHeaderValidity() {
        // Given

        // When

        // Then
    }
}