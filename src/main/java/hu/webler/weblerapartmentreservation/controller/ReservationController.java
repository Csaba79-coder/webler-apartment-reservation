package hu.webler.weblerapartmentreservation.controller;

import hu.webler.weblerapartmentreservation.domain.invoice.persistence.InvoiceRepository;
import hu.webler.weblerapartmentreservation.domain.reservation.entity.Reservation;
import hu.webler.weblerapartmentreservation.domain.reservation.service.ReservationService;
import hu.webler.weblerapartmentreservation.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;
    private final InvoiceRepository invoiceRepository;

    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        return ResponseEntity.status(200).body(reservationService.renderAllReservations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        return ResponseEntity.status(200).body(reservationService.renderReservationById(id));
    }

    @GetMapping("/start-date/{startDate}")
    public List<Reservation> getReservationByStartDate(@PathVariable LocalDate startDate) {
        return reservationService.renderReservationByStarDate(startDate);
    }

    @GetMapping("/user/{userId}")
    public List<Reservation> getReservationByUser(@PathVariable Long userId) {
        return reservationService.renderReservationByUserId(userId);
    }

    @GetMapping("/apartment/{apartmentId}")
    public List<Reservation> getReservationByApartmentId(@PathVariable Long apartmentId) {
        return reservationService.renderReservationByApartmentId(apartmentId);
    }
}
