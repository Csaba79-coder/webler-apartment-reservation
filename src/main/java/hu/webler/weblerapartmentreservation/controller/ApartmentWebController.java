package hu.webler.weblerapartmentreservation.controller;

import hu.webler.weblerapartmentreservation.domain.apartment.service.ApartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/thy")
public class ApartmentWebController {

    private final ApartmentService apartmentService;

    @GetMapping
    public String homePage() {
        return "index";
    }

    @GetMapping("index")
    public String renderAllApartments(Model model) {
        model.addAttribute("index", apartmentService.findAllApartments());
        return "index";
    }

    @GetMapping("/index/{id}")
    public String renderApartmentById(@PathVariable Long id, Model model) {
        model.addAttribute("index", apartmentService.findApartmentById(id));
        return "index";
    }
}
