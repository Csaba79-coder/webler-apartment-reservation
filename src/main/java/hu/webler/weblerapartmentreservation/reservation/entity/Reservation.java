package hu.webler.weblerapartmentreservation.reservation.entity;

import hu.webler.weblerapartmentreservation.apartment.entity.Apartment;
import hu.webler.weblerapartmentreservation.invoice.entity.Invoice;
import hu.webler.weblerapartmentreservation.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate startDate;
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "apartment_id")
    private Apartment apartment;

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;
}
