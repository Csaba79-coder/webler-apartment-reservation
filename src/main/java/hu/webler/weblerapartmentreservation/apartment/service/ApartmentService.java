package hu.webler.weblerapartmentreservation.apartment.service;

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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApartmentService {

    private final ApartmentRepository apartmentRepository;
    private final AddressService addressService;

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
                    return new RuntimeException(message);
                        });
    }

    public ApartmentModel createApartment(ApartmentCreateModel apartmentCreateModel) {
        // TODO logic, if address is in database set that, if not, save first a new address, than save!
        // similar logic as in User ...
        return ApartmentMapper.mapApartmentEntityToApartmentModel(apartmentRepository.save(ApartmentMapper.mapApartmentCreateModelToApartmentEntity(apartmentCreateModel)));
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
