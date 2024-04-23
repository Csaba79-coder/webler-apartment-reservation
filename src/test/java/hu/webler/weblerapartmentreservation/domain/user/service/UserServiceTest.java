package hu.webler.weblerapartmentreservation.domain.user.service;

import hu.webler.weblerapartmentreservation.domain.address.entity.Address;
import hu.webler.weblerapartmentreservation.domain.address.persistence.AddressRepository;
import hu.webler.weblerapartmentreservation.domain.user.entity.User;
import hu.webler.weblerapartmentreservation.domain.user.model.UserModel;
import hu.webler.weblerapartmentreservation.domain.user.persistance.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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

        when(userRepository.findById(random)).thenReturn(Optional.ofNullable(user));

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
}