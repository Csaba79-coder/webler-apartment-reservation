package hu.webler.weblerapartmentreservation.domain.reservation.service;

import hu.webler.weblerapartmentreservation.domain.apartment.entity.Apartment;
import hu.webler.weblerapartmentreservation.domain.apartment.model.ApartmentCreateModel;
import hu.webler.weblerapartmentreservation.domain.apartment.persistance.ApartmentRepository;
import hu.webler.weblerapartmentreservation.domain.reservation.entity.Reservation;
import hu.webler.weblerapartmentreservation.domain.reservation.model.ReservationCreateModel;
import hu.webler.weblerapartmentreservation.domain.reservation.model.ReservationModel;
import hu.webler.weblerapartmentreservation.domain.reservation.persistance.ReservationRepository;
import hu.webler.weblerapartmentreservation.domain.reservation.util.ReservationMapper;
import hu.webler.weblerapartmentreservation.domain.user.entity.User;
import hu.webler.weblerapartmentreservation.domain.user.model.UserCreateModel;
import hu.webler.weblerapartmentreservation.domain.user.persistance.UserRepository;
import hu.webler.weblerapartmentreservation.domain.user.util.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ApartmentRepository apartmentRepository;
    private final UserRepository userRepository;

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
