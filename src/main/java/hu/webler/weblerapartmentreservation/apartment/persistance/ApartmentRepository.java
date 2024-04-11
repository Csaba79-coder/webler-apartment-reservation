package hu.webler.weblerapartmentreservation.apartment.persistance;

import hu.webler.weblerapartmentreservation.apartment.entity.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, Long> {
}
