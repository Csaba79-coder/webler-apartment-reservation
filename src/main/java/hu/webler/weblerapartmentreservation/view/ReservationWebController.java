package hu.webler.weblerapartmentreservation.view;

import hu.webler.weblerapartmentreservation.domain.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/thy")
public class ReservationWebController {

    private final ReservationService reservationService;

    @GetMapping("/reservations")
    public String renderAllReservations(Model model) {
        model.addAttribute("reservations", reservationService.renderAllReservations());
        return "reservation";
    }
}
