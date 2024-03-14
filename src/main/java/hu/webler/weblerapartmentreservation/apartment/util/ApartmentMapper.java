package hu.webler.weblerapartmentreservation.apartment.util;

import hu.webler.weblerapartmentreservation.apartment.entity.Apartment;
import hu.webler.weblerapartmentreservation.apartment.entity.ApartmentDetail;
import hu.webler.weblerapartmentreservation.apartment.model.ApartmentCreateModel;
import hu.webler.weblerapartmentreservation.apartment.model.ApartmentDetailCreateModel;
import hu.webler.weblerapartmentreservation.apartment.model.ApartmentDetailModel;
import hu.webler.weblerapartmentreservation.apartment.model.ApartmentModel;

public class ApartmentMapper {

    public static ApartmentModel mapApartmentEntityToApartmentModel(Apartment apartment) {
        ApartmentModel apartmentModel = new ApartmentModel();
        apartmentModel.setTitle(apartment.getTitle());
        apartmentModel.setDescription(apartment.getDescription());
        apartmentModel.setPrice(apartment.getPrice());
        apartmentModel.setFloorNumber(apartment.getFloorNumber());
        apartmentModel.setRoomNumber(apartment.getRoomNumber());
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

    public static ApartmentDetailModel mapApartmentDetailEntityToApartmentDetailModel(ApartmentDetail apartmentDetail) {
        ApartmentDetailModel model = new ApartmentDetailModel();
        model.setBeds(apartmentDetail.getBeds());
        model.setRooms(apartmentDetail.getRooms());
        model.setMinGuest(apartmentDetail.getMinGuest());
        model.setMaxGuest(apartmentDetail.getMaxGuest());
        return model;
    }

    public static ApartmentDetail mapApartmentDetailCreatModelToApartmentEntity(ApartmentDetailCreateModel apartmentDetailCreateModel) {
        ApartmentDetail apartmentDetail = new ApartmentDetail();
        apartmentDetail.setBeds(apartmentDetailCreateModel.getBeds());
        apartmentDetail.setRooms(apartmentDetailCreateModel.getRooms());
        apartmentDetail.setMinGuest(apartmentDetailCreateModel.getMinGuest());
        apartmentDetail.setMaxGuest(apartmentDetailCreateModel.getMaxGuest());
        return apartmentDetail;
    }
}
