package ru.interid.animalbase.service;

import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {
    private final static String TEMPLATE_PATH = "src/main/resources/templates/excel";
    private final static String TEMPLATE = "template.xlsx";

    public Path getTemplatePath() {
        return Paths.get(TEMPLATE_PATH + "/" + TEMPLATE);
    }
}
