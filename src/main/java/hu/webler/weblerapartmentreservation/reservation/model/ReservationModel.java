package hu.webler.weblerapartmentreservation.reservation.model;

import hu.webler.weblerapartmentreservation.apartment.entity.Apartment;
import hu.webler.weblerapartmentreservation.invoice.entity.Invoice;
import hu.webler.weblerapartmentreservation.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationModel {

    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private User user;
    private Apartment apartment;
    private Invoice invoice;
}
