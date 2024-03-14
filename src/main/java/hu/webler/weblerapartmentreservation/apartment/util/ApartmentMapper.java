package hu.webler.weblerapartmentreservation.apartment.util;

import hu.webler.weblerapartmentreservation.apartment.entity.Apartment;
import hu.webler.weblerapartmentreservation.apartment.model.ApartmentCreateModel;
import hu.webler.weblerapartmentreservation.apartment.model.ApartmentModel;

public class ApartmentMapper {

    public static ApartmentModel mapApartmentEntityToApartmentModel(Apartment apartment) {
        ApartmentModel apartmentModel = new ApartmentModel();
        apartmentModel.setId(apartment.getId());
        return apartmentModel;
    }

    public static Apartment mapApartmentCreateModelToApartmentEntity(ApartmentCreateModel apartmentCreateModel) {
        Apartment apartment = new Apartment();
        apartment.setTitle(apartmentCreateModel.getTitle());
        apartment.setDescription(apartmentCreateModel.getDescription());
        apartment.setPrice(apartmentCreateModel.getPrice());
        apartment.setFloorNumber(apartmentCreateModel.getFloorNumber());
        apartment.setRoomNumber(apartmentCreateModel.getRoomNumber());
        return apartment;
    }

    private ApartmentMapper() {
    }
}
