package ru.interid.animalbase.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/download-template")
@RequiredArgsConstructor
public class FileDownloadController {
    private final static String TEMPLATE_DIRECTORY = "src/main/resources/templates/excel";
    private final static String TEMPLATE_DOC_NAME = "template.xlsx";
    private final static Path TEMPLATE_PATH = Paths.get(TEMPLATE_DIRECTORY + "/" + TEMPLATE_DOC_NAME);

    @GetMapping
    public ResponseEntity<InputStreamResource> downloadTemplate() throws IOException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", TEMPLATE_DOC_NAME);

        InputStreamResource resource = new InputStreamResource(Files.newInputStream(TEMPLATE_PATH));
        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }
}