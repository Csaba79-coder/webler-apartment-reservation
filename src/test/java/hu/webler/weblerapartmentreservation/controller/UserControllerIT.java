package hu.webler.weblerapartmentreservation.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.webler.weblerapartmentreservation.domain.address.entity.Address;
import hu.webler.weblerapartmentreservation.domain.reservation.entity.Reservation;
import hu.webler.weblerapartmentreservation.domain.user.entity.User;
import hu.webler.weblerapartmentreservation.domain.user.model.UserModel;
import hu.webler.weblerapartmentreservation.domain.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@ContextConfiguration(classes = {UserController.class})
@ExtendWith(MockitoExtension.class)
@DisplayName("User controller test")
public class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    private User user;
    private UserModel userModel;

    @BeforeEach
    public void init() {
        user = new User();
        userModel = new UserModel();
    }

    @Test
    @DisplayName("Given empty list when findAllUsers then return empty list")
    public void givenEmptyList_whenFindAllUsers_thenReturnsEmptyList() throws Exception {
        // Given
        given(userService.findAllUsers()).willReturn(Collections.emptyList());
        List<UserModel> expectedModel = new ArrayList<>();

        // When
        MvcResult result = mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andReturn();

        List<UserModel> actualModels = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

        // Then
        assertThat(actualModels)
                .usingRecursiveComparison()
                .asList()
                .isEmpty();
        assertThat(actualModels).isEqualTo(expectedModel);
        assertThat(actualModels)
                .usingRecursiveComparison()
                .asList()
                .hasSize(0);
    }

    @Test
    @DisplayName("Given non-empty list when findAllUsers then return list")
    public void givenNonEmptyList_whenFindAllUsers_thenReturnNonEmptyList() throws Exception {
        // Given
        Long userId1 = 1L;
        Long userId2 = 2L;

        List<UserModel> expectedModels = Arrays.asList(
                new UserModel(userId1, "Test", "Test", "Test", "Test", new Address()),
                new UserModel(userId2, "Test 2", "Test 2", "Test 2", "Test 2", new Address())
        );

        given(userService.findAllUsers()).willReturn(expectedModels);

        // When
        MvcResult result = mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        List<UserModel> actualModels = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

        assertThat(actualModels)
                .usingRecursiveComparison()
                .asList()
                .isNotEmpty();
        assertThat(actualModels)
                .usingRecursiveComparison()
                .isEqualTo(expectedModels);
        assertThat(actualModels)
                .usingRecursiveComparison()
                .asList()
                .hasSize(expectedModels.size());
    }

    @Test
    @DisplayName("Given valid id when findUserById then return User")
    public void givenValidId_whenFindUserById_thenReturnUser() throws Exception {
        // Given
        Long id = new Random().nextLong();
        Address address = new Address(1L, "Test", "Test", "Test", "Test");
        List<Reservation> reservations = Arrays.asList(
                new Reservation(),
                new Reservation()
        );

        // Mock
        User user = new User(id, "Test", "Test", "Test", "Test", address, reservations);
        when(userService.findUserById(any(Long.class))).thenReturn(user);

        // When
        MvcResult result = mockMvc.perform(get("/api/users/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        assertThat(id).isNotNull();
        assertThat(id).isEqualTo(user.getId());

        String responseContent = result.getResponse().getContentAsString();
        UserModel actualUser = objectMapper.readValue(responseContent, UserModel.class);

        assertThat(actualUser)
                .usingRecursiveComparison()
                .isEqualTo(user);
    }

    @Test
    @DisplayName("Given invalid id when findUserById then throw NoSuchElementException")
    public void givenInvalidId_whenFindUserById_thenThrowNoSuchElementException() throws Exception {
        long userId = 1L;

        when(mockMvc.perform(get("/api/users/{id}", userId)))
                .thenThrow(new NoSuchElementException());
    }

    @Test
    @DisplayName("Given valid id when deleteUserById then delete user")
    public void givenValidId_whenDeleteUserById_thenDeleteUser() throws Exception {
        // Given
        Long id = new Random().nextLong();
        List<Reservation> reservations = Arrays.asList(
                new Reservation(),
                new Reservation()
        );

        // Mock
        User user = new User(id, "test", "test", "test","test",
                new Address(), reservations);

        // When / then
        MvcResult result = mockMvc.perform(delete("/api/users/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    @DisplayName("Given invalid id when deleteUserById then throw NoSuchElementException")
    public void givenInvalidId_whenDeleteUserById_thenThrowNoSuchElementException() throws Exception {
        // Given
        Long userId = 1L;

        // When / Then
        when(mockMvc.perform(delete("/api/users/{id}", userId)))
                .thenThrow(new NoSuchElementException());
    }
}