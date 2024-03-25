package hu.webler.weblerapartmentreservation.address.util;

import hu.webler.weblerapartmentreservation.address.entity.Address;
import hu.webler.weblerapartmentreservation.address.model.AddressCreateModel;
import hu.webler.weblerapartmentreservation.address.model.AddressModel;
import hu.webler.weblerapartmentreservation.address.model.AddressUpdateModel;

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

    public static Address mapAddressUpdateModelToAddressEntity(AddressUpdateModel addressUpdateModel) {
        Address address = new Address();
        if (addressUpdateModel.getCountry() != null) {
            address.setCountry(addressUpdateModel.getCountry());
        }
        if (addressUpdateModel.getPostalCode() != null) {
            address.setPostalCode(addressUpdateModel.getPostalCode());
        }
        if (addressUpdateModel.getCity() != null) {
            address.setCity(addressUpdateModel.getCity());
        }
        if (addressUpdateModel.getAddressLine() != null) {
            address.setAddressLine(addressUpdateModel.getAddressLine());
        }
        return address;
    }

    private AddressMapper(){
    }
}
