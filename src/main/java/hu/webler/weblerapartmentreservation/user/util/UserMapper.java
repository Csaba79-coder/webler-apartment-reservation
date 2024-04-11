package hu.webler.weblerapartmentreservation.user.util;

import hu.webler.weblerapartmentreservation.address.entity.Address;
import hu.webler.weblerapartmentreservation.user.entity.User;
import hu.webler.weblerapartmentreservation.user.model.UserCreateModel;
import hu.webler.weblerapartmentreservation.user.model.UserModel;
import hu.webler.weblerapartmentreservation.user.model.UserUpdateModel;

import java.util.Optional;

public class UserMapper {

    public static UserModel mapUserEntityToUserModel(User user) {
        UserModel userModel = new UserModel();
        userModel.setId(user.getId());
        userModel.setFirstName(user.getFirstName());
        userModel.setLastName(user.getLastName());
        userModel.setEmail(user.getEmail());
        userModel.setPhoneNumber(user.getPhoneNumber());
        return userModel;
    }


    public static User mapUserCreateModelToUserEntity(UserCreateModel userCreateModel, Address address) {
        User user = new User();
        user.setFirstName(userCreateModel.getFirstName());
        user.setLastName(userCreateModel.getLastName());
        user.setEmail(userCreateModel.getEmail());
        user.setPhoneNumber(userCreateModel.getPhoneNumber());
        user.setAddress(address);
        return user;
    }

    public static void mapUserUpdateModelToUserEntity(User user, UserUpdateModel userUpdateModel) {
        Optional.ofNullable(userUpdateModel.getFirstName()).ifPresent(user::setFirstName);
        Optional.ofNullable(userUpdateModel.getLastName()).ifPresent(user::setLastName);
        Optional.ofNullable(userUpdateModel.getEmail()).ifPresent(user::setEmail);
        Optional.ofNullable(userUpdateModel.getPhoneNumber()).ifPresent(user::setPhoneNumber);
    }

    private UserMapper() {
    }
}