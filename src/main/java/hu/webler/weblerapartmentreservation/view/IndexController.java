package hu.webler.weblerapartmentreservation.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping({"", "/", "/index", "/index.html"})
    public String homePage() {
        return "index";
    }
}
