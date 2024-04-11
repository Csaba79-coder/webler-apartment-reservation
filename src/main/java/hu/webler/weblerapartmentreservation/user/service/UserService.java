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
        // TODO same logic as the other places!!!
        Address addressFound = addressService.findAddressById(userCreateModel.getAddress().getId());
        if (addressFound != null) {
            userCreateModel.setAddress(addressFound);
        } else {
            // save address first!
            // If the address is not found, save it first
            Address address = new Address();
            address.setCity(userCreateModel.getAddress().getCity());
            address.setCountry(userCreateModel.getAddress().getCountry());
            address.setPostalCode(userCreateModel.getAddress().getPostalCode());
            address.setLine(userCreateModel.getAddress().getLine());
            Address addressToSave = addressRepository.save(address);
            userCreateModel.setAddress(addressToSave);
        }
        return UserMapper.mapUserEntityToUserModel(userRepository.save(UserMapper.mapUserCreateModelToUserEntity(userCreateModel)));
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
