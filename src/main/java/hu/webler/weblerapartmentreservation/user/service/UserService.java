package hu.webler.weblerapartmentreservation.user.service;

import hu.webler.weblerapartmentreservation.address.entity.Address;
import hu.webler.weblerapartmentreservation.address.model.AddressCreateModel;
import hu.webler.weblerapartmentreservation.address.model.AddressModel;
import hu.webler.weblerapartmentreservation.address.persistence.AddressRepository;
import hu.webler.weblerapartmentreservation.address.service.AddressService;
import hu.webler.weblerapartmentreservation.address.util.AddressMapper;
import hu.webler.weblerapartmentreservation.user.entity.User;
import hu.webler.weblerapartmentreservation.user.model.UserCreateModel;
import hu.webler.weblerapartmentreservation.user.model.UserModel;
import hu.webler.weblerapartmentreservation.user.model.UserUpdateModel;
import hu.webler.weblerapartmentreservation.user.persistance.UserRepository;
import hu.webler.weblerapartmentreservation.user.util.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final AddressService addressService;
    private final AddressRepository addressRepository;

    public List<UserModel> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::mapUserEntityToUserModel)
                .collect(Collectors.toList());
    }

    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow( () -> {
                    String message = String.format("User with id %d was not found", id);
                    log.info(message);
                    return new RuntimeException(message);
                });
    }

    public UserModel createUser(UserCreateModel userCreateModel) {
        Address addressToSave = null;
        // Check if address details are provided
        if (userCreateModel.getAddress() != null) {
            // Attempt to find an existing address matching the provided details
            Optional<Address> optionalAddress = addressRepository.findAddressByCountryAndPostalCodeAndCityAndLine(
                    userCreateModel.getAddress().getCountry(),
                    userCreateModel.getAddress().getPostalCode(),
                    userCreateModel.getAddress().getCity(),
                    userCreateModel.getAddress().getLine());

            if (optionalAddress.isPresent()) {
                // If found, use the existing address
                addressToSave = optionalAddress.get();
            } else {
                // If not found, create a new address using the provided details
                AddressCreateModel model = new AddressCreateModel();
                model.setCountry(userCreateModel.getAddress().getCountry());
                model.setPostalCode(userCreateModel.getAddress().getPostalCode());
                model.setCity(userCreateModel.getAddress().getCity());
                model.setLine(userCreateModel.getAddress().getLine()); // Ensure line is not null
                addressToSave = createAddress(model);
                // Remove this line since the address is already saved inside createAddress
                // addressRepository.save(addressToSave);
            }
        } else {
            // Throw an exception or return an error indicating that address details are required
            throw new IllegalArgumentException("Address details are required");
        }

        // Set the address to the userCreateModel
        userCreateModel.setAddress(addressToSave);

        // Save the user entity and return the mapped user model
        return UserMapper.mapUserEntityToUserModel(userRepository.save(UserMapper.mapUserCreateModelToUserEntity(userCreateModel)));
    }

    private Address createAddress(AddressCreateModel addressCreateModel) {
        Address address = new Address();
        address.setCountry(addressCreateModel.getCountry());
        address.setPostalCode(addressCreateModel.getPostalCode());
        address.setCity(addressCreateModel.getCity());

        // Check if the line is not null before setting it
        if (addressCreateModel.getLine() != null) {
            address.setLine(addressCreateModel.getLine());
        } else {
            // You may choose to throw an exception or set a default value for line
            // For now, let's set it to an empty string
            address.setLine("");
        }

        return addressRepository.save(address);
    }

    public void deleteUser(Long id) {
        findUserById(id);
        userRepository.deleteById(id);
    }

    public UserModel updateUser(Long id, UserUpdateModel userUpdateModel) {
        User user = findUserById(id);
        if (user != null) {
            UserMapper.mapUserUpdateModelToUserEntity(user, userUpdateModel);
            user = userRepository.save(user);
            return UserMapper.mapUserEntityToUserModel(user);
        }
        return null;
    }
}
