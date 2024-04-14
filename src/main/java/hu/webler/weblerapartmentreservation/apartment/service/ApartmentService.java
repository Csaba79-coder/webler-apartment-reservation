package hu.webler.weblerapartmentreservation.apartment.service;

import hu.webler.weblerapartmentreservation.address.entity.Address;
import hu.webler.weblerapartmentreservation.address.model.AddressCreateModel;
import hu.webler.weblerapartmentreservation.address.persistence.AddressRepository;
import hu.webler.weblerapartmentreservation.address.service.AddressService;
import hu.webler.weblerapartmentreservation.apartment.entity.Apartment;
import hu.webler.weblerapartmentreservation.apartment.model.ApartmentCreateModel;
import hu.webler.weblerapartmentreservation.apartment.model.ApartmentModel;
import hu.webler.weblerapartmentreservation.apartment.model.ApartmentUpdateModel;
import hu.webler.weblerapartmentreservation.apartment.persistance.ApartmentRepository;
import hu.webler.weblerapartmentreservation.apartment.util.ApartmentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApartmentService {

    private final ApartmentRepository apartmentRepository;
    private final AddressService addressService;
    private final AddressRepository addressRepository;

    public List<ApartmentModel> findAllApartments() {
        return apartmentRepository.findAll()
                .stream()
                .map(ApartmentMapper::mapApartmentEntityToApartmentModel)
                .collect(Collectors.toList());
    }

    public Apartment findApartmentById(Long id) {
        return apartmentRepository.findById(id)
                .orElseThrow( () -> {
                    String message = String.format("Apartment with id %d was not found", id);
                    log.info(message);
                    return new NoSuchElementException(message);
                        });
    }

    public ApartmentModel createApartment(ApartmentCreateModel apartmentCreateModel) {
        Address addressToSave = null;
        if (apartmentCreateModel.getAddress() != null) {
            Optional<Address> optionalAddress = addressRepository.findAddressByCountryAndPostalCodeAndCityAndLine(
                    apartmentCreateModel.getAddress().getCountry(),
                    apartmentCreateModel.getAddress().getPostalCode(),
                    apartmentCreateModel.getAddress().getCity(),
                    apartmentCreateModel.getAddress().getLine());

            if (optionalAddress.isPresent()) {
                addressToSave = optionalAddress.get();
            } else {
                AddressCreateModel model = new AddressCreateModel();
                model.setCountry(apartmentCreateModel.getAddress().getCountry());
                model.setPostalCode(apartmentCreateModel.getAddress().getPostalCode());
                model.setCity(apartmentCreateModel.getAddress().getCity());
                model.setLine(apartmentCreateModel.getAddress().getLine());
                addressToSave = createAddress(model);
            }
        } else {
            throw new IllegalArgumentException("Address details are required");
        }
        apartmentCreateModel.setAddress(addressToSave);
        return ApartmentMapper.mapApartmentEntityToApartmentModel(apartmentRepository.save(ApartmentMapper.mapApartmentCreateModelToApartmentEntity(apartmentCreateModel)));
    }

    private Address createAddress(AddressCreateModel addressCreateModel) {
        Address address = new Address();
        address.setCountry(addressCreateModel.getCountry());
        address.setPostalCode(addressCreateModel.getPostalCode());
        address.setCity(addressCreateModel.getCity());

        if (addressCreateModel.getLine() != null) {
            address.setLine(addressCreateModel.getLine());
        } else {
            address.setLine("");
        }
        return addressRepository.save(address);
    }

    public void deleteApartment(Long id) {
        findApartmentById(id);
        apartmentRepository.deleteById(id);
    }

    public ApartmentModel updateApartment(Long id, ApartmentUpdateModel apartmentUpdateModel) {
        Apartment apartment = findApartmentById(id);
        if (apartment != null) {
            ApartmentMapper.mapApartmentUpdateModelToApartmentEntity(apartment, apartmentUpdateModel);
            apartment = apartmentRepository.save(apartment);
            return ApartmentMapper.mapApartmentEntityToApartmentModel(apartment);
        }
        return null;
    }
}
