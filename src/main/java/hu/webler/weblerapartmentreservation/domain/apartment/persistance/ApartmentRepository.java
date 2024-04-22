package hu.webler.weblerapartmentreservation.domain.apartment.persistance;

import hu.webler.weblerapartmentreservation.domain.apartment.entity.Apartment;
import hu.webler.weblerapartmentreservation.domain.apartment.value.ApartmentStatus;
import hu.webler.weblerapartmentreservation.domain.apartment.value.ApartmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, Long> {

    Optional<Apartment> findApartmentByFloorNumberAndRoomNumberAndMinGuestAndMaxGuestAndApartmentTypeAndDescriptionAndApartmentStatusAndPrice(
            Integer floorNumber, Integer roomNumber, Integer minGuest, Integer maxGuest, ApartmentType apartmentType,
            String description, ApartmentStatus apartmentStatus, BigDecimal price);
}
