package hu.webler.weblerapartmentreservation.view;

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


    @GetMapping("/apartments")
    public String renderAllApartments(Model model) {
        model.addAttribute("apartments", apartmentService.findAllApartments());
        return "apartment";
    }

    @GetMapping("/apartments/{id}")
    public String renderApartmentById(@PathVariable Long id, Model model) {
        model.addAttribute("apartments", apartmentService.findApartmentById(id));
        return "apartment";
    }
}
