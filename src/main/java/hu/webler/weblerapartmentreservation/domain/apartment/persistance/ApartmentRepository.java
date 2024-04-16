package hu.webler.weblerapartmentreservation.domain.apartment.persistance;

import hu.webler.weblerapartmentreservation.domain.apartment.entity.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, Long> {
}
