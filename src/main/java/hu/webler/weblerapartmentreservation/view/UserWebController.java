package hu.webler.weblerapartmentreservation.view;

import hu.webler.weblerapartmentreservation.domain.user.persistance.UserRepository;
import hu.webler.weblerapartmentreservation.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/thy")
public class UserWebController {

    private final UserService userService;

    @GetMapping("/users")
    public String renderAllUsers(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "user";
    }
}
