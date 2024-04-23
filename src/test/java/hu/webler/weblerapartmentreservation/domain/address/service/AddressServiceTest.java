package hu.webler.weblerapartmentreservation.domain.address.service;

import hu.webler.weblerapartmentreservation.domain.address.entity.Address;
import hu.webler.weblerapartmentreservation.domain.address.model.AddressCreateModel;
import hu.webler.weblerapartmentreservation.domain.address.model.AddressModel;
import hu.webler.weblerapartmentreservation.domain.address.persistence.AddressRepository;
import hu.webler.weblerapartmentreservation.domain.address.util.AddressMapper;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Address service test - unit test")
public class AddressServiceTest {

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private AddressService addressService;

    @Test
    @DisplayName("Given empty address list when findAllAddress() then returns empty list")
    public void givenEmptyAddressList_whenGetAllAddress_thenReturnsEmptyList() {
        // Given
        when(addressRepository.findAll()).thenReturn(Collections.emptyList());

        // When
        List<AddressModel> addresses = addressService.findAllAddress();

        // Then
        then(addresses).isEmpty();
        assertThat(addresses).isEmpty();
    }

    @Test
    @DisplayName("Given a non empty address list when findAllAddress() then return list of address models")
    public void givenNonEmptyAddressList_whenGetAllAddress_thenReturnListOfAddressModels() {
        // Given
        List<Address> addressData = List.of(
                new Address(1L, "country1", "postalCode1", "city1", "line1"),
                new Address(2L, "country2", "postalCode2", "city2", "line2")
        );

        when(addressRepository.findAll()).thenReturn(addressData);

        // When
        List<AddressModel> addresses = addressService.findAllAddress();

        // Then
        then(addresses).hasSize(2);
        assertThat(addresses).hasSize(2);
        assertThat(addresses)
                .usingRecursiveComparison()
                .isEqualTo(addressData);
    }

    @Test
    @DisplayName("Given valid address id when findAddressById() then returns address entity")
    public void givenValidAddressId_whenFindAddressById_thenReturnsAddressEntity() {
        // Given
        Long random = new Random().nextLong(1, 4);

        List<Address> addressData = List.of(
                new Address(1L, "country1", "postalCode1", "city1", "line1"),
                new Address(2L, "country2", "postalCode2", "city2", "line2"),
                new Address(3L, "country3", "postalCode3", "city3", "line3"),
                new Address(4L, "country4", "postalCode4", "city4", "line4")
        );

        Address address = addressData.get(random.intValue() - 1);
        Long searchId = address.getId();

        when(addressRepository.findById(random)).thenReturn(Optional.ofNullable(address));

        // When
        Address addressResult = addressService.findAddressById(searchId);

        // Then
        assertThat(addressResult).isNotNull();
        assertThat(addressResult)
                .isEqualTo(address);
    }

    @Test
    @DisplayName("Given invalid address id when findAddressById() then throws no such element exception")
    public void givenInvalidAddressId_whenFindAddressById_thenThrowsNoSuchElementException() {
        // Given
        Long searchId = new Random().nextLong();

        // When / Then
        assertThatThrownBy(() -> addressService.findAddressById(searchId))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Address with id " + searchId + " was not found");
    }

    @Test
    @DisplayName("Given valid addressCreateModel when creating address then returns address model")
    public void givenValidAddressCreateModel_whenCreatingAddress_thenReturnsAddressModel() {
        // Given
        AddressCreateModel addressCreateModel = new AddressCreateModel();
        addressCreateModel.setCountry("testCountry");
        addressCreateModel.setPostalCode("testPostalCode");
        addressCreateModel.setCity("testCity");
        addressCreateModel.setLine("testLine");

        // Mock
        AddressModel expectedModel = new AddressModel();
        expectedModel.setId(new Random().nextLong(1, 100));
        expectedModel.setCountry("testCountry");
        expectedModel.setPostalCode("testPostalCode");
        expectedModel.setCity("testCity");
        expectedModel.setLine("testLine");

        when(addressRepository.save(any())).thenReturn(AddressMapper.mapAddressCreateModelToAddressEntity(addressCreateModel));

        // When
        AddressModel createdAddressModel = addressService.createAddress(addressCreateModel);

        // Then
        then(expectedModel)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(createdAddressModel);
    }

    @Test
    @DisplayName("Given valid address id when deleting address then verify")
    public void givenValidAddressId_whenDeletingAddress_thenVerify() {
        // Given
        Long id = new Random().nextLong();
        Address address = AddressMapper.mapAddressCreateModelToAddressEntity
                (new AddressCreateModel("test", "test", "Test", "Test"));
        address.setId(id);
        addressRepository.save(address);

        when(addressRepository.findById(id)).thenReturn(Optional.ofNullable(address));

        // When
        addressService.deleteAddress(id);

        // Then
        verify(addressRepository).deleteById(any());
    }
}
