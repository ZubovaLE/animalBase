package ru.interid.animalbase.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.interid.animalbase.mapper.MassLoadService;


@Controller
@RequestMapping("/animals")
@RequiredArgsConstructor
public class AnimalController {
    private final MassLoadService massLoadService;

    @GetMapping
    public String getAnimalsPage(Model model) {
        return "animals/animals";
    }

    @GetMapping("/dogs")
    public String getDogsPage(Model model) {
        return "animals/dogs";
    }

    @GetMapping("/birds")
    public String getBirdsPage(Model model) {
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
