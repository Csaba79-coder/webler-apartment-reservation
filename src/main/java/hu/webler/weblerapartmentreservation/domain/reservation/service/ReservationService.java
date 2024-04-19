package hu.webler.weblerapartmentreservation.domain.reservation.service;

import hu.webler.weblerapartmentreservation.domain.reservation.entity.Reservation;
import hu.webler.weblerapartmentreservation.domain.reservation.persistance.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public List<Reservation>  renderAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation renderReservationById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> {
                    String message = String.format("Reservation with id %d was not found", id);
                    log.info(message);
                    return new NoSuchElementException(message);
                });
    }

    public List<Reservation> renderReservationByStarDate(LocalDate startDate) {
        return reservationRepository.findByStartDate(startDate);
    }

    public List<Reservation> renderReservationByApartmentId(Long apartmentId) {
        return reservationRepository.findByApartmentId(apartmentId);
    }

    public List<Reservation> renderReservationByUserId(Long userId) {
        return reservationRepository.findByUserId(userId);
    }
}
