package ru.interid.animalbase.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.interid.animalbase.mapper.MassLoadService;
import ru.interid.animalbase.service.BirdService;
import ru.interid.animalbase.service.DogService;


@Controller
@RequestMapping("/animals")
@RequiredArgsConstructor
public class AnimalController {
    private final MassLoadService massLoadService;
    private final DogService dogService;
    private final BirdService birdService;

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
    public String importFromExcel(@RequestParam("file") MultipartFile file, HttpServletResponse httpServletResponse) {
//        massLoadService.addAll(parserService.getDtos());
        return "success/successfulImport";
    }
}