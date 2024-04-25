package hu.webler.weblerapartmentreservation.domain.user.service;

import hu.webler.weblerapartmentreservation.domain.address.entity.Address;
import hu.webler.weblerapartmentreservation.domain.address.persistence.AddressRepository;
import hu.webler.weblerapartmentreservation.domain.user.entity.User;
import hu.webler.weblerapartmentreservation.domain.user.model.UserCreateModel;
import hu.webler.weblerapartmentreservation.domain.user.model.UserModel;
import hu.webler.weblerapartmentreservation.domain.user.persistance.UserRepository;
import hu.webler.weblerapartmentreservation.domain.user.util.UserMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("User service test - unit test")
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Given empty user list when findAllUsers() then returns empty list")
    public void givenEmptyUserList_whenGetAllUsers_thenReturnsEmptyList() {
        // Given
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        // When
        List<UserModel> users = userService.findAllUsers();

        // Then
        assertThat(users).isEmpty();
    }

    @Test
    @DisplayName("Given a non empty user list when findAllUsers() then return list of user models")
    public void givenNonEmptyUserList_whenGetAllUsers_thenReturnListOfUserModels() {
        // Given
        List<User> userData = List.of(
                new User(1L, "test data", "test data", "test data",
                        "test data", new Address(), new ArrayList<>()),
                new User(2L, "test data", "test data", "test data",
                        "test data", new Address(), new ArrayList<>())
        );

        when(userRepository.findAll()).thenReturn(userData);

        // When
        List<UserModel> users = userService.findAllUsers();

        // Then
        assertThat(users).hasSize(2);
        assertThat(users)
                .usingRecursiveComparison()
                .isEqualTo(userData);
    }

    @Test
    @DisplayName("Given valid user id when findUserById() then returns user entity")
    public void givenValidUserId_whenFindUserById_thenReturnsUserEntity() {
        // Given
        Long random = new Random().nextLong(1, 4);

        List<User> userData = List.of(
                new User(1L, "test data", "test data", "test data",
                        "test data", new Address(), new ArrayList<>()),
                new User(2L, "test data", "test data", "test data",
                        "test data", new Address(), new ArrayList<>()),
                new User(3L, "test data", "test data", "test data",
                        "test data", new Address(), new ArrayList<>()),
                new User(4L, "test data", "test data", "test data",
                        "test data", new Address(), new ArrayList<>())
        );

        User user = userData.get(random.intValue() - 1);
        Long searchId = user.getId();

        when(userRepository.findById(random)).thenReturn(Optional.of(user));

        // When
        User userResult = userService.findUserById(searchId);

        // Then
        assertThat(userResult).isNotNull();
        assertThat(userResult)
                .isEqualTo(user);
    }

    @Test
    @DisplayName("Given invalid user id when findUserById() then throws no such element exception")
    public void givenInvalidUserId_whenFindUserById_thenThrowsNoSuchElementException() {
        // Given
        Long searchId = new Random().nextLong(1, 100);

        // When / Then
        assertThatThrownBy(() -> userService.findUserById(searchId))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("User with id " + searchId + " was not found");
    }

    @Test
    @DisplayName("Given valid userCreateModel when creating user then returns user model")
    public void givenValidUserCreateModel_whenCreatingUser_thenReturnsUserModel() {
        // Given
        Address address = new Address(1L, "Test data", "Test data", "Test data", "Test data");

        UserCreateModel userCreateModel = new UserCreateModel();
        userCreateModel.setFirstName("First Name");
        userCreateModel.setLastName("Last Name");
        userCreateModel.setEmail("Email");
        userCreateModel.setPhoneNumber("PhoneNumber");
        userCreateModel.setAddress(address);

        // Mock
        UserModel expectedModel = new UserModel();
        expectedModel.setId(1L);
        expectedModel.setFirstName("First Name");
        expectedModel.setLastName("Last Name");
        expectedModel.setEmail("Email");
        expectedModel.setPhoneNumber("PhoneNumber");
        expectedModel.setAddress(address);

        when(userRepository.save(any())).thenReturn(UserMapper.mapUserCreateModelToUserEntity(userCreateModel));

        // When
        UserModel createdUserModel = userService.createUser(userCreateModel);

        // Then
        then(expectedModel)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(createdUserModel);
    }

    @Test
    @DisplayName("Given missing address info when creating user then throws IllegalArgumentException")
    public void givenMissingAddress_whenCreatingApartment_thenThrowsIllegalArgumentException() {
        // Given

        UserCreateModel userCreateModel = new UserCreateModel();
        userCreateModel.setFirstName("First Name");
        userCreateModel.setLastName("Last Name");
        userCreateModel.setEmail("Email");
        userCreateModel.setPhoneNumber("PhoneNumber");

        // When / Then
        assertThatThrownBy(() -> userService.createUser(userCreateModel))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Address details are required");
    }

    @Test
    @DisplayName("Given missing user info when creating user then throws NullPointerException")
    public void givenMissingUserInfo_whenCreatingUser_thenThrowsNullPointerException() {
        // Given
        Address address = new Address();

        UserCreateModel userCreateModel = new UserCreateModel();
        userCreateModel.setFirstName("First Name");
        userCreateModel.setEmail("Email");
        userCreateModel.setPhoneNumber("PhoneNumber");
        userCreateModel.setAddress(address);

        // When / Then
        assertThatThrownBy(() -> userService.createUser(userCreateModel))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Given valid user id when deleting user then verify")
    public void givenValidUserId_whenDeletingAddress_thenVerify() {
        // Given
        Long id = new Random().nextLong();
        User user = UserMapper.mapUserCreateModelToUserEntity(
                new UserCreateModel("First Name", "Last Name", "Email", "PhoneNumber", new Address()));

        user.setId(id);
        userRepository.save(user);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        // When
        userService.deleteUser(id);

        // Then#
        verify(userRepository).deleteById(any());
    }
}