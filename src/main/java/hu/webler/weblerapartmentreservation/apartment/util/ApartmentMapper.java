package hu.webler.weblerapartmentreservation.apartment.util;

import hu.webler.weblerapartmentreservation.apartment.entity.Apartment;
import hu.webler.weblerapartmentreservation.apartment.model.ApartmentCreateModel;
import hu.webler.weblerapartmentreservation.apartment.model.ApartmentModel;
import hu.webler.weblerapartmentreservation.apartment.model.ApartmentUpdateModel;

public class ApartmentMapper {

    public static ApartmentModel mapApartmentEntityToApartmentModel(Apartment apartment) {
        ApartmentModel apartmentModel = new ApartmentModel();
        apartmentModel.setId(apartment.getId());
        apartmentModel.setFloorNumber(apartment.getFloorNumber());
        apartmentModel.setRoomNumber(apartment.getRoomNumber());
        apartmentModel.setMinGuest(apartment.getMinGuest());
        apartmentModel.setMaxGuest(apartment.getMaxGuest());
        apartmentModel.setApartmentType(apartment.getApartmentType());
        apartmentModel.setDescription(apartment.getDescription());
        apartmentModel.setApartmentStatus(apartment.getApartmentStatus());
        apartmentModel.setPrice(apartment.getPrice());
        return apartmentModel;
    }

    public static Apartment mapApartmentCreateModelToApartmentEntity(ApartmentCreateModel apartmentCreateModel) {
        Apartment apartment = new Apartment();
        apartment.setFloorNumber(apartmentCreateModel.getFloorNumber());
        apartment.setRoomNumber(apartmentCreateModel.getRoomNumber());
        apartment.setMinGuest(apartmentCreateModel.getMinGuest());
        apartment.setMaxGuest(apartmentCreateModel.getMaxGuest());
        apartment.setApartmentType(apartmentCreateModel.getApartmentType());
        apartment.setDescription(apartmentCreateModel.getDescription());
        apartment.setPrice(apartmentCreateModel.getPrice());
        return apartment;
    }

    public static Apartment mapApartmentUpdateModelToApartmentEntity(ApartmentUpdateModel apartmentUpdateModel) {
        Apartment apartment = new Apartment();
        if (apartmentUpdateModel.getFloorNumber() != null) {
            apartment.setFloorNumber(apartmentUpdateModel.getFloorNumber());
        }
        if (apartmentUpdateModel.getRoomNumber() != null) {
            apartment.setRoomNumber(apartmentUpdateModel.getRoomNumber());
        }
        if (apartmentUpdateModel.getMinGuest() != null) {
            apartment.setMinGuest(apartmentUpdateModel.getMinGuest());
        }
        if (apartmentUpdateModel.getMaxGuest() != null) {
            apartment.setMaxGuest(apartmentUpdateModel.getMaxGuest());
        }
        if (apartmentUpdateModel.getApartmentType() != null) {
            apartment.setApartmentType(apartmentUpdateModel.getApartmentType());
        }
        if (apartmentUpdateModel.getDescription() != null) {
            apartment.setDescription(apartmentUpdateModel.getDescription());
        }
        if (apartmentUpdateModel.getApartmentStatus() != null) {
            apartment.setApartmentStatus(apartmentUpdateModel.getApartmentStatus());
        }
        if (apartmentUpdateModel.getPrice() != null) {
            apartment.setPrice(apartmentUpdateModel.getPrice());
        }
        return apartment;
    }

    private ApartmentMapper() {
    }
}
