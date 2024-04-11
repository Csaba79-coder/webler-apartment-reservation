package hu.webler.weblerapartmentreservation.user.service;

import hu.webler.weblerapartmentreservation.address.entity.Address;
import hu.webler.weblerapartmentreservation.address.persistence.AddressRepository;
import hu.webler.weblerapartmentreservation.address.service.AddressService;
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

    public UserModel createUser(UserCreateModel userCreateModel, Long addressId) {
        Address address = addressService.findAddressById(addressId);
        return UserMapper.mapUserEntityToUserModel(userRepository.save(UserMapper.mapUserCreateModelToUserEntity(userCreateModel, address)));
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
