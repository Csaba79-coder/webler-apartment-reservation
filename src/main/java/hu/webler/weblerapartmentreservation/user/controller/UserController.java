package hu.webler.weblerapartmentreservation.user.controller;

import hu.webler.weblerapartmentreservation.address.entity.Address;
import hu.webler.weblerapartmentreservation.user.entity.User;
import hu.webler.weblerapartmentreservation.user.model.UserCreateModel;
import hu.webler.weblerapartmentreservation.user.model.UserModel;
import hu.webler.weblerapartmentreservation.user.model.UserUpdateModel;
import hu.webler.weblerapartmentreservation.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<UserModel>> renderAllUsers() {
        return ResponseEntity.status(200).body(userService.findAllUsers());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> findUserById(@PathVariable Long id) {
        return ResponseEntity.status(200).body(userService.findUserById(id));
    }

    @PostMapping("/users/address/{addressId}")
    public ResponseEntity<UserModel> createUser(@RequestBody UserCreateModel userCreateModel,@PathVariable(value = "addressId") Long addressId) {
        return ResponseEntity.status(200).body(userService.createUser(userCreateModel, addressId));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.status(204).build();
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<UserModel> updateUser(@PathVariable Long id, @RequestBody UserUpdateModel userUpdateModel) {
        return ResponseEntity.status(200).body(userService.updateUser(id, userUpdateModel));
    }
}
