package hu.webler.weblerapartmentreservation.view;

import hu.webler.weblerapartmentreservation.domain.address.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/thy")
public class AddressWebController {

    private final AddressService addressService;

    @GetMapping("/addresses")
    public String renderAllAddresses(Model model) {
        model.addAttribute("addresses", addressService.findAllAddress());
        return "address";
    }
}
