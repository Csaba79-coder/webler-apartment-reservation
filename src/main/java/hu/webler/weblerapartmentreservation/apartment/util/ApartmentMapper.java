package hu.webler.weblerapartmentreservation.apartment.util;

import hu.webler.weblerapartmentreservation.apartment.entity.Apartment;
import hu.webler.weblerapartmentreservation.apartment.model.ApartmentCreateModel;
import hu.webler.weblerapartmentreservation.apartment.model.ApartmentModel;
import hu.webler.weblerapartmentreservation.apartment.model.ApartmentUpdateModel;

import java.util.Optional;

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

    public static Apartment mapApartmentUpdateModelToApartmentEntity(Apartment apartment, ApartmentUpdateModel apartmentUpdateModel) {
        Optional.ofNullable(apartmentUpdateModel.getFloorNumber()).ifPresent(apartment::setFloorNumber);
        Optional.ofNullable(apartmentUpdateModel.getRoomNumber()).ifPresent(apartment::setRoomNumber);
        Optional.ofNullable(apartmentUpdateModel.getMinGuest()).ifPresent(apartment::setMinGuest);
        Optional.ofNullable(apartmentUpdateModel.getMaxGuest()).ifPresent(apartment::setMaxGuest);
        Optional.ofNullable(apartmentUpdateModel.getApartmentType()).ifPresent(apartment::setApartmentType);
        Optional.ofNullable(apartmentUpdateModel.getDescription()).ifPresent(apartment::setDescription);
        Optional.ofNullable(apartmentUpdateModel.getApartmentStatus()).ifPresent(apartment::setApartmentStatus);
        Optional.ofNullable(apartmentUpdateModel.getPrice()).ifPresent(apartment::setPrice);
        return apartment;
    }

    private ApartmentMapper() {
    }
}
