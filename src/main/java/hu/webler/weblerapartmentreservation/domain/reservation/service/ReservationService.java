package hu.webler.weblerapartmentreservation.domain.reservation.service;

import hu.webler.weblerapartmentreservation.domain.address.entity.Address;
import hu.webler.weblerapartmentreservation.domain.address.model.AddressCreateModel;
import hu.webler.weblerapartmentreservation.domain.address.persistence.AddressRepository;
import hu.webler.weblerapartmentreservation.domain.apartment.entity.Apartment;
import hu.webler.weblerapartmentreservation.domain.apartment.model.ApartmentCreateModel;
import hu.webler.weblerapartmentreservation.domain.apartment.persistance.ApartmentRepository;
import hu.webler.weblerapartmentreservation.domain.invoice.entity.Invoice;
import hu.webler.weblerapartmentreservation.domain.invoice.model.InvoiceCreateModel;
import hu.webler.weblerapartmentreservation.domain.invoice.persistence.InvoiceRepository;
import hu.webler.weblerapartmentreservation.domain.reservation.entity.Reservation;
import hu.webler.weblerapartmentreservation.domain.reservation.model.ReservationCreateModel;
import hu.webler.weblerapartmentreservation.domain.reservation.model.ReservationModel;
import hu.webler.weblerapartmentreservation.domain.reservation.persistance.ReservationRepository;
import hu.webler.weblerapartmentreservation.domain.user.entity.User;
import hu.webler.weblerapartmentreservation.domain.user.model.UserCreateModel;
import hu.webler.weblerapartmentreservation.domain.user.persistance.UserRepository;
import hu.webler.weblerapartmentreservation.domain.user.util.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import static hu.webler.weblerapartmentreservation.domain.address.util.AddressMapper.mapAddressCreateModelToAddressEntity;
import static hu.webler.weblerapartmentreservation.domain.apartment.util.ApartmentMapper.mapApartmentCreateModelToApartmentEntity;
import static hu.webler.weblerapartmentreservation.domain.invoice.util.InvoiceMapper.mapInvoiceCreateModelToInvoiceEntity;
import static hu.webler.weblerapartmentreservation.domain.reservation.util.ReservationMapper.mapReservationCreateModelToReservationEntity;
import static hu.webler.weblerapartmentreservation.domain.reservation.util.ReservationMapper.mapReservationEntityToReservationModel;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ApartmentRepository apartmentRepository;
    private final UserRepository userRepository;
    private final InvoiceRepository invoiceRepository;
    private final AddressRepository addressRepository;

    public List<Reservation> renderAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation renderReservationById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> {
                    String message = String.format("Reservation with id %d was not found", id);
                    log.info(message);
                    return new NoSuchElementException(message);
                });
    }

    public List<Reservation> renderReservationByStarDate(LocalDate startDate) {
        return reservationRepository.findByStartDate(startDate);
    }

    public List<Reservation> renderReservationByApartmentId(Long apartmentId) {
        return reservationRepository.findByApartmentId(apartmentId);
    }

    public List<Reservation> renderReservationByUserId(Long userId) {
        return reservationRepository.findByUserId(userId);
    }

    public ReservationModel createReservation(ReservationCreateModel reservationCreateModel) {
        ReservationCreateModel reservation = new ReservationCreateModel();
        AddressCreateModel address = new AddressCreateModel();

        address.setCountry(reservationCreateModel.getApartment().getAddress().getCountry());
        address.setCity(reservationCreateModel.getApartment().getAddress().getCity());
        address.setLine(reservationCreateModel.getApartment().getAddress().getLine());
        address.setPostalCode(reservationCreateModel.getApartment().getAddress().getPostalCode());
        Address addressToSave = addressRepository.save(mapAddressCreateModelToAddressEntity(address));

        ApartmentCreateModel apartment = new ApartmentCreateModel();
        apartment.setApartmentType(reservationCreateModel.getApartment().getApartmentType());
        apartment.setPrice(reservationCreateModel.getApartment().getPrice());
        apartment.setDescription(reservationCreateModel.getApartment().getDescription());
        apartment.setMinGuest(reservationCreateModel.getApartment().getMinGuest());
        apartment.setMaxGuest(reservationCreateModel.getApartment().getMaxGuest());
        apartment.setFloorNumber(reservationCreateModel.getApartment().getFloorNumber());
        apartment.setRoomNumber(reservationCreateModel.getApartment().getRoomNumber());
        apartment.setAddress(addressToSave);
        Apartment apartmentToSave = apartmentRepository.save(mapApartmentCreateModelToApartmentEntity(apartment));
        reservation.setApartment(apartmentToSave);

        InvoiceCreateModel invoice = new InvoiceCreateModel();
        invoice.setPaymentType(reservationCreateModel.getInvoice().getPaymentType());
        invoice.setAddress(addressToSave);
        Invoice invoiceToSave = invoiceRepository.save(mapInvoiceCreateModelToInvoiceEntity(invoice));
        reservation.setInvoice(invoiceToSave);

        UserCreateModel user = new UserCreateModel();
        user.setEmail(reservationCreateModel.getUser().getEmail());
        user.setFirstName(reservationCreateModel.getUser().getFirstName());
        user.setLastName(reservationCreateModel.getUser().getLastName());
        user.setPhoneNumber(reservationCreateModel.getUser().getPhoneNumber());
        user.setAddress(addressToSave);

        User userToSave = userRepository.save(UserMapper.mapUserCreateModelToUserEntity(user));
        reservation.setUser(userToSave);

        reservation.setStartDate(reservationCreateModel.getStartDate());
        reservation.setEndDate(reservationCreateModel.getEndDate());

        return mapReservationEntityToReservationModel(reservationRepository.save(mapReservationCreateModelToReservationEntity(reservation)));
    }
}
