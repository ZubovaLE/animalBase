package ru.interid.animalbase.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.interid.animalbase.mapper.AnimalExcelParserService;
import ru.interid.animalbase.mapper.MassLoadService;
import ru.interid.animalbase.model.MassLoadAnimalData;
import ru.interid.animalbase.service.BirdService;
import ru.interid.animalbase.service.DogService;

import static org.apache.commons.lang3.StringUtils.isNotBlank;


@Controller
@RequestMapping("/animals")
@RequiredArgsConstructor
public class AnimalController {
    private final MassLoadService massLoadService;
    private final DogService dogService;
    private final BirdService birdService;
    private final AnimalExcelParserService animalExcelParserService;

    @GetMapping
    public String getAnimalsPage() {
        return "animals/animals";
    }

    @GetMapping("/dogs")
    public String getDogsPage(Model model) {
        model.addAttribute("dogs", dogService.getAll());
        return "animals/dogs";
    }

    @Transactional
    @GetMapping("/birds")
    public String getBirdsPage(Model model) {
        model.addAttribute("birds", birdService.getAll());
        return "animals/birds";
    }

    @GetMapping("/import")
    public String getImportPage() {
        return "animals/import";
    }

    @PostMapping("/import")
    public String importFromExcel(Model model, @RequestParam("file") MultipartFile file, HttpServletResponse httpServletResponse) {
        MassLoadAnimalData data = animalExcelParserService.parseExcelDocument(file);
        String errorInfo = data.getErrorInfo();
        if (isNotBlank(errorInfo)) {
            model.addAttribute("errorMessage", errorInfo);
            return "errors/404";
        } else {
            massLoadService.addAll(data.getAnimalDtos());
            model.addAttribute("successMessage", "Данные загружены успешно");
            return "success/successfulImport";
        }
    }
}