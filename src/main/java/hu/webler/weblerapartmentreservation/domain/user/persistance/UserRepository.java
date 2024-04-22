package hu.webler.weblerapartmentreservation.domain.user.persistance;

import hu.webler.weblerapartmentreservation.domain.address.entity.Address;
import hu.webler.weblerapartmentreservation.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByFirstNameAndLastNameAndEmailAndPhoneNumber(String firstName, String lastName, String email,
                                                                                   String phoneNumber);
}
