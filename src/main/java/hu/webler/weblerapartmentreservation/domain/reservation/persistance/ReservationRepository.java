package hu.webler.weblerapartmentreservation.domain.reservation.persistance;

import hu.webler.weblerapartmentreservation.domain.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByStartDate(LocalDate startDate);
    List<Reservation> findByUserId(Long userId);
    List<Reservation> findByApartmentId(Long apartmentId);
}
