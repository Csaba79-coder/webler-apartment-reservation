package hu.webler.weblerapartmentreservation.controller;

import hu.webler.weblerapartmentreservation.domain.apartment.service.ApartmentService;
import hu.webler.weblerapartmentreservation.domain.reservation.entity.Reservation;
import hu.webler.weblerapartmentreservation.domain.reservation.model.ReservationCreateModel;
import hu.webler.weblerapartmentreservation.domain.reservation.model.ReservationModel;
import hu.webler.weblerapartmentreservation.domain.reservation.service.ReservationService;
import hu.webler.weblerapartmentreservation.domain.user.service.UserService;
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
    private final UserService userService;
    private final ApartmentService apartmentService;

    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        return ResponseEntity.status(200).body(reservationService.renderAllReservations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        return ResponseEntity.status(200).body(reservationService.renderReservationById(id));
    }

    @GetMapping("/start-date/{startDate}")
    public ResponseEntity<List<Reservation>> getReservationByStartDate(@PathVariable LocalDate startDate) {
        return ResponseEntity.status(200).body(reservationService.renderReservationByStarDate(startDate)) ;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Reservation>> getReservationByUser(@PathVariable Long userId) {
        userService.findUserById(userId);
        return ResponseEntity.status(200).body(reservationService.renderReservationByUserId(userId));
    }

    @GetMapping("/apartment/{apartmentId}")
    public ResponseEntity<List<Reservation>> getReservationByApartmentId(@PathVariable Long apartmentId) {
        apartmentService.findApartmentById(apartmentId);
        return ResponseEntity.status(200).body(reservationService.renderReservationByApartmentId(apartmentId));
    }

    @PostMapping
    public ResponseEntity<ReservationModel> createReservation(@RequestBody ReservationCreateModel reservationCreateModel) {
        return ResponseEntity.status(200).body(reservationService.createReservation(reservationCreateModel));
    }
}
