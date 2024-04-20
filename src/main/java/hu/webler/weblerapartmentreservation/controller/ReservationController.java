package hu.webler.weblerapartmentreservation.controller;

import hu.webler.weblerapartmentreservation.domain.invoice.persistence.InvoiceRepository;
import hu.webler.weblerapartmentreservation.domain.reservation.entity.Reservation;
import hu.webler.weblerapartmentreservation.domain.reservation.persistance.ReservationRepository;
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
    private final ReservationRepository reservationRepository;

    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        return ResponseEntity.status(200).body(reservationService.renderAllReservations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        getReservationByApartmentId(id);
        return ResponseEntity.status(200).body(reservationService.renderReservationById(id));
    }

    @GetMapping("/start-date/{startDate}")
    public List<Reservation> getReservationByStartDate(@PathVariable LocalDate startDate) {
        getReservationByStartDate(startDate);
        return reservationService.renderReservationByStarDate(startDate);
    }

    @GetMapping("/user/{userId}")
    public List<Reservation> getReservationByUser(@PathVariable Long userId) {
        getReservationByUser(userId);
        return reservationService.renderReservationByUserId(userId);
    }

    @GetMapping("/apartment/{apartmentId}")
    public List<Reservation> getReservationByApartmentId(@PathVariable Long apartmentId) {
        getReservationByApartmentId(apartmentId);
        return reservationService.renderReservationByApartmentId(apartmentId);
    }
}
