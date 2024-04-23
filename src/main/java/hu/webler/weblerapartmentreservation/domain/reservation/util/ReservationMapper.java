package hu.webler.weblerapartmentreservation.domain.reservation.util;

import hu.webler.weblerapartmentreservation.domain.reservation.entity.Reservation;
import hu.webler.weblerapartmentreservation.domain.reservation.model.ReservationCreateModel;
import hu.webler.weblerapartmentreservation.domain.reservation.model.ReservationModel;
import hu.webler.weblerapartmentreservation.domain.reservation.model.ReservationUpdateModel;

import java.util.Optional;

public class ReservationMapper {

    public static ReservationModel mapReservationEntityToReservationModel(Reservation reservation) {
        ReservationModel reservationModel = new ReservationModel();
        reservationModel.setId(reservation.getId());
        reservationModel.setStartDate((reservation.getStartDate()));
        reservationModel.setEndDate(reservation.getEndDate());
        reservationModel.setUser(reservation.getUser());
        reservationModel.setApartment(reservation.getApartment());
        reservationModel.setInvoice(reservation.getInvoice());
        return reservationModel;
    }

    public static Reservation mapReservationCreateModelToReservationEntity(ReservationCreateModel model) {
        Reservation reservation = new Reservation();
            reservation.setStartDate(model.getStartDate());
            reservation.setEndDate(model.getEndDate());
            reservation.setUser(model.getUser());
            reservation.setApartment(model.getApartment());
            reservation.setInvoice(model.getInvoice());
        return reservation;
    }

    public static void mapReservationUpdateModelToReservationEntity(Reservation reservation, ReservationUpdateModel reservationUpdateModel) {
        Optional.ofNullable(reservationUpdateModel.getStartDate()).ifPresent(reservation::setStartDate);
        Optional.ofNullable(reservationUpdateModel.getEndDate()).ifPresent(reservation::setEndDate);
        Optional.ofNullable(reservationUpdateModel.getUser()).ifPresent(reservation::setUser);
        Optional.ofNullable(reservationUpdateModel.getApartment()).ifPresent(reservation::setApartment);
        Optional.ofNullable(reservationUpdateModel.getInvoice()).ifPresent(reservation::setInvoice);
    }

    private ReservationMapper() {

    }
}
