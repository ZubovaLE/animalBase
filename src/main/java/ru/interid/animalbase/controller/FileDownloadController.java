package ru.interid.animalbase.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.interid.animalbase.service.FileService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Controller
@RequestMapping("/download-template")
@RequiredArgsConstructor
public class FileDownloadController {
    private final FileService fileService;

    @GetMapping
    public ResponseEntity<InputStreamResource> downloadTemplate() throws IOException {
        Path templatePath = fileService.getTemplatePath();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", templatePath.getFileName().toString());

        InputStreamResource resource = new InputStreamResource(Files.newInputStream(templatePath));
        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }
}