package hu.webler.weblerapartmentreservation.address.persistence;

import hu.webler.weblerapartmentreservation.address.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findAddressById(Long id);
    Optional<Address> findAddressByCountryAndPostalCodeAndCityAndLine(String country, String postalCode, String city, String line);
}
