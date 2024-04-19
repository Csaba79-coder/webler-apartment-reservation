package hu.webler.weblerapartmentreservation.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/thy")
public class SightsWebController {

    @GetMapping("/sights")
    public String renderSightsPage() {
        return "sight";
    }
}
