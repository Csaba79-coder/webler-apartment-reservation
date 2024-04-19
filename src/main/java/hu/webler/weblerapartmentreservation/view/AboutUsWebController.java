package hu.webler.weblerapartmentreservation.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/thy")
public class AboutUsWebController {

    @GetMapping("/about-us")
    public String renderAboutUsPage() {
        return "about-us";
    }
}
