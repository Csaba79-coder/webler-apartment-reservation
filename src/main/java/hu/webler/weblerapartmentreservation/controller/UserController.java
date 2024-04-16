package hu.webler.weblerapartmentreservation.controller;

import hu.webler.weblerapartmentreservation.domain.user.entity.User;
import hu.webler.weblerapartmentreservation.domain.user.model.UserCreateModel;
import hu.webler.weblerapartmentreservation.domain.user.model.UserModel;
import hu.webler.weblerapartmentreservation.domain.user.model.UserUpdateModel;
import hu.webler.weblerapartmentreservation.domain.user.service.UserService;
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

    @PostMapping("/users")
    public ResponseEntity<UserModel> createUser(@RequestBody UserCreateModel userCreateModel) {
        return ResponseEntity.status(200).body(userService.createUser(userCreateModel));
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
