package hu.webler.weblerapartmentreservation.user.util;

import hu.webler.weblerapartmentreservation.user.entity.User;
import hu.webler.weblerapartmentreservation.user.model.UserCreateModel;
import hu.webler.weblerapartmentreservation.user.model.UserModel;
import hu.webler.weblerapartmentreservation.user.model.UserUpdateModel;

public class UserMapper {

    public static UserModel mapUserEntityToUserModel(User user) {
        UserModel userModel = new UserModel();
        userModel.setId(user.getId());
        userModel.setFirstName(user.getFirstName());
        userModel.setLastName(user.getLastName());
        userModel.setEmail(user.getEmail());
        userModel.setPhoneNumber(userModel.getPhoneNumber());
        return userModel;
    }


    public static User mapUserCreateModelToUserEntity(UserCreateModel userCreateModel) {
        User user = new User();
        user.setFirstName(userCreateModel.getFirstName());
        user.setLastName(userCreateModel.getLastName());
        user.setEmail(userCreateModel.getEmail());
        user.setPhoneNumber(userCreateModel.getPhoneNumber());
        return user;
    }

    public static User mapUserUpdateModelToUserEntity(UserUpdateModel userUpdateModel) {
        User user = new User();
        if (userUpdateModel.getFirstName() != null) {
            user.setFirstName(userUpdateModel.getFirstName());
        }
        if (userUpdateModel.getLastName() != null) {
            user.setLastName(userUpdateModel.getLastName());
        }
        if (userUpdateModel.getEmail() != null) {
            user.setEmail(userUpdateModel.getEmail());
        }
        if (userUpdateModel.getPhoneNumber() != null) {
            user.setPhoneNumber(userUpdateModel.getPhoneNumber());
        }
        return user;
    }

    private UserMapper() {
    }
}