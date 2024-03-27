package ru.interid.animalbase.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.interid.animalbase.mapper.AnimalMapper;
import ru.interid.animalbase.mapper.MassLoadService;

import java.util.List;


@Controller
@RequestMapping("/animals")
@RequiredArgsConstructor
public class AnimalController {
    //    private Map<Animal.AnimalType, AnimalService<? extends Animal>> services;
//
//    public AnimalController(List<AnimalService<? extends Animal>> services) {
//        this.services = services.stream()
//                .collect
//                        (Collectors.toMap(AnimalService::getType, Function.identity()));
//    }
    private final AnimalMapper animalMapper;
    private final MassLoadService massLoadService;

    @GetMapping
    public String getAnimalsPage(Model model) {
//        model.addAttribute("animals", null);
        return "animals/animals";
    }

    @GetMapping("/dogs")
    public String getDogsPage(Model model) {
//        model.addAttribute("animals", null);
        return "animals/dogs";
    }

    @GetMapping("/birds")
    public String getBirdsPage(Model model) {
//        model.addAttribute("animals", null);
        return "animals/birds";
    }

    @GetMapping("/import")
    public String getImportPage() {
        return "animals/import";
    }

    @PostMapping("/import")
    public String importFromExcel(@RequestParam("file") MultipartFile file, HttpServletResponse httpServletResponse) {
        massLoadService.addAll(animalMapper.getAnimals(List.of()));
        return "success/successfulImport";
    }
}
