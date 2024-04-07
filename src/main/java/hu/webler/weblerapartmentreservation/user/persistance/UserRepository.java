package hu.webler.weblerapartmentreservation.user.persistance;

import hu.webler.weblerapartmentreservation.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findApartmentById(Long id);
}
