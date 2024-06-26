package hu.webler.weblerapartmentreservation.domain.user.util;

import hu.webler.weblerapartmentreservation.domain.user.entity.User;
import hu.webler.weblerapartmentreservation.domain.user.model.UserCreateModel;
import hu.webler.weblerapartmentreservation.domain.user.model.UserModel;
import hu.webler.weblerapartmentreservation.domain.user.model.UserUpdateModel;

import java.util.Optional;

public class UserMapper {

    public static UserModel mapUserEntityToUserModel(User user) {
        UserModel userModel = new UserModel();
        userModel.setId(user.getId());
        userModel.setFirstName(user.getFirstName());
        userModel.setLastName(user.getLastName());
        userModel.setEmail(user.getEmail());
        userModel.setPhoneNumber(user.getPhoneNumber());
        userModel.setAddress(user.getAddress());
        return userModel;
    }


    public static User mapUserCreateModelToUserEntity(UserCreateModel userCreateModel) {
        User user = new User();
        user.setFirstName(userCreateModel.getFirstName());
        user.setLastName(userCreateModel.getLastName());
        user.setEmail(userCreateModel.getEmail());
        user.setPhoneNumber(userCreateModel.getPhoneNumber());
        user.setAddress(userCreateModel.getAddress());
        return user;
    }

    public static void mapUserUpdateModelToUserEntity(User user, UserUpdateModel userUpdateModel) {
        // TODO do not forget about address here!
        Optional.ofNullable(userUpdateModel.getFirstName()).ifPresent(user::setFirstName);
        Optional.ofNullable(userUpdateModel.getLastName()).ifPresent(user::setLastName);
        Optional.ofNullable(userUpdateModel.getEmail()).ifPresent(user::setEmail);
        Optional.ofNullable(userUpdateModel.getPhoneNumber()).ifPresent(user::setPhoneNumber);
    }

    private UserMapper() {
    }
}