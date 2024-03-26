package hu.webler.weblerapartmentreservation.address.util;

import hu.webler.weblerapartmentreservation.address.entity.Address;
import hu.webler.weblerapartmentreservation.address.model.AddressCreateModel;
import hu.webler.weblerapartmentreservation.address.model.AddressModel;
import hu.webler.weblerapartmentreservation.address.model.AddressUpdateModel;

import java.util.Optional;

public class AddressMapper {

    public static AddressModel mapAddressEntityToAddressModel(Address address) {
        AddressModel addressModel = new AddressModel();
        addressModel.setId(address.getId());
        addressModel.setCountry(address.getCountry());
        addressModel.setPostalCode(address.getPostalCode());
        addressModel.setCity(address.getCity());
        addressModel.setAddressLine(address.getAddressLine());
        return addressModel;
    }

    public static Address mapAddressCreateModelToAddressEntity(AddressCreateModel addressCreateModel) {
        Address address = new Address();
        address.setCountry(addressCreateModel.getCountry());
        address.setPostalCode(addressCreateModel.getPostalCode());
        address.setCity(addressCreateModel.getCity());
        address.setAddressLine(addressCreateModel.getAddressLine());
        return address;
    }

    public static void mapAddressUpdateModelToAddressEntity(Address address, AddressUpdateModel addressUpdateModel) {
        Optional.ofNullable(addressUpdateModel.getCountry()).ifPresent(address::setCountry);
        Optional.ofNullable(addressUpdateModel.getPostalCode()).ifPresent(address::setPostalCode);
        Optional.ofNullable(addressUpdateModel.getCity()).ifPresent(address::setCity);
        Optional.ofNullable(addressUpdateModel.getAddressLine()).ifPresent(address::setAddressLine);
    }

    private AddressMapper(){
    }
}
