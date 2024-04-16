package hu.webler.weblerapartmentreservation.domain.reservation.model;

import hu.webler.weblerapartmentreservation.domain.apartment.entity.Apartment;
import hu.webler.weblerapartmentreservation.domain.invoice.entity.Invoice;
import hu.webler.weblerapartmentreservation.domain.user.entity.User;
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
