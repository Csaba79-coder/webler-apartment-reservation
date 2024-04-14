package hu.webler.weblerapartmentreservation.address.service;

import hu.webler.weblerapartmentreservation.address.entity.Address;
import hu.webler.weblerapartmentreservation.address.model.AddressCreateModel;
import hu.webler.weblerapartmentreservation.address.model.AddressModel;
import hu.webler.weblerapartmentreservation.address.model.AddressUpdateModel;
import hu.webler.weblerapartmentreservation.address.persistence.AddressRepository;
import hu.webler.weblerapartmentreservation.address.util.AddressMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressService {

    private final AddressRepository addressRepository;

    public List<AddressModel> findAllAddress() {
        return addressRepository.findAll()
                .stream()
                .map(AddressMapper::mapAddressEntityToAddressModel)
                .collect(Collectors.toList());
    }

    public Address findAddressById(Long id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> {
                    String message = String.format("Address with id %d was not found", id);
                    log.info(message);
                    return new NoSuchElementException(message);
                });
    }

    public AddressModel createAddress(AddressCreateModel addressCreateModel) {
        return AddressMapper.mapAddressEntityToAddressModel(addressRepository
                .save(AddressMapper.mapAddressCreateModelToAddressEntity(addressCreateModel)));
    }

    public void deleteAddress(Long id) {
        findAddressById(id);
        addressRepository.deleteById(id);
    }

    public AddressModel updateAddress(Long id, AddressUpdateModel addressUpdateModel) {
        Address address = findAddressById(id);
        if (address != null) {
            AddressMapper.mapAddressUpdateModelToAddressEntity(address, addressUpdateModel);
            address = addressRepository.save(address);
            return AddressMapper.mapAddressEntityToAddressModel(address);
        }
        return null;
    }
}
