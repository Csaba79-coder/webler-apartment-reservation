package hu.webler.weblerapartmentreservation.view;

import hu.webler.weblerapartmentreservation.domain.apartment.service.ApartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/thy")
public class ApartmentWebController {

    private final ApartmentService apartmentService;

    @GetMapping
    public String homePage() {
        return "index";
    }

    @GetMapping("/apartments")
    public String renderAllApartments(Model model) {
        model.addAttribute("apartments", apartmentService.findAllApartments());
        return "apartments";
    }
}
