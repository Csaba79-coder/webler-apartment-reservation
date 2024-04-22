package hu.webler.weblerapartmentreservation.domain.reservation.service;

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
import hu.webler.weblerapartmentreservation.domain.reservation.util.ReservationMapper;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ApartmentRepository apartmentRepository;
    private final UserRepository userRepository;
    private final InvoiceRepository invoiceRepository;

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
        User userToSave = null;
        if (reservationCreateModel.getUser() != null) {
            Optional<User> optionalUser = userRepository.findUserByFirstNameAndLastNameAndEmailAndPhoneNumber(
                    reservationCreateModel.getUser().getFirstName(),
                    reservationCreateModel.getUser().getLastName(),
                    reservationCreateModel.getUser().getEmail(),
                    reservationCreateModel.getUser().getPhoneNumber());

            if (optionalUser.isPresent()) {
                userToSave = optionalUser.get();
            } else {
                UserCreateModel model = new UserCreateModel();
                model.setFirstName(reservationCreateModel.getUser().getFirstName());
                model.setLastName(reservationCreateModel.getUser().getLastName());
                model.setEmail(reservationCreateModel.getUser().getEmail());
                model.setPhoneNumber(reservationCreateModel.getUser().getPhoneNumber());
                userToSave = createUser(model);
            }
        } else {
            throw new IllegalArgumentException("User details are required");
        }
        reservationCreateModel.setUser(userToSave);

        Apartment apartmentToSave = null;
        if (reservationCreateModel.getApartment() != null) {
            Optional<Apartment> optionalApartment = apartmentRepository.findApartmentByFloorNumberAndRoomNumberAndMinGuestAndMaxGuestAndApartmentTypeAndDescriptionAndApartmentStatusAndPrice(
                    reservationCreateModel.getApartment().getFloorNumber(),
                    reservationCreateModel.getApartment().getRoomNumber(),
                    reservationCreateModel.getApartment().getMinGuest(),
                    reservationCreateModel.getApartment().getMaxGuest(),
                    reservationCreateModel.getApartment().getApartmentType(),
                    reservationCreateModel.getApartment().getDescription(),
                    reservationCreateModel.getApartment().getApartmentStatus(),
                    reservationCreateModel.getApartment().getPrice());

            if (optionalApartment.isPresent()) {
                apartmentToSave = optionalApartment.get();
            } else {
                ApartmentCreateModel model = new ApartmentCreateModel();
                model.setFloorNumber(reservationCreateModel.getApartment().getFloorNumber());
                model.setRoomNumber(reservationCreateModel.getApartment().getRoomNumber());
                model.setMinGuest(reservationCreateModel.getApartment().getMinGuest());
                model.setMaxGuest(reservationCreateModel.getApartment().getMaxGuest());
                model.setApartmentType(reservationCreateModel.getApartment().getApartmentType());
                model.setDescription(reservationCreateModel.getApartment().getDescription());
                model.setApartmentStatus(reservationCreateModel.getApartment().getApartmentStatus());
                model.setPrice(reservationCreateModel.getApartment().getPrice());
                apartmentToSave = createApartment(model);
            }
        } else {
            throw new IllegalArgumentException("Apartment details are required");
        }
        reservationCreateModel.setApartment(apartmentToSave);

        Invoice invoiceToSave = null;
        if (reservationCreateModel.getInvoice() != null) {
            Optional<Invoice> optionalInvoice = invoiceRepository.findInvoiceByGenerationDateAndPaymentTypeAndPaymentDate(
                    reservationCreateModel.getInvoice().getGenerationDate(),
                    reservationCreateModel.getInvoice().getPaymentType(),
                    reservationCreateModel.getInvoice().getPaymentDate());

            if (optionalInvoice.isPresent()) {
                invoiceToSave = optionalInvoice.get();
            } else {
                InvoiceCreateModel model = new InvoiceCreateModel();
                model.setGenerationDate(reservationCreateModel.getInvoice().getGenerationDate());
                model.setPaymentType(reservationCreateModel.getInvoice().getPaymentType());
                model.setPaymentDate(reservationCreateModel.getInvoice().getPaymentDate());
                invoiceToSave = createInvoice(model);
            }
        }else {
                throw new IllegalArgumentException("Invoice details are required");
            }
        reservationCreateModel.setInvoice(invoiceToSave);
        return ReservationMapper.mapReservationEntityToReservationModel(reservationRepository.save(ReservationMapper.mapReservationCreateModelToReservationEntity(reservationCreateModel)));
    }

    private User createUser(UserCreateModel userCreatemodel) {
        User user = new User();
        user.setFirstName(userCreatemodel.getFirstName());
        user.setLastName(userCreatemodel.getLastName());
        user.setEmail(userCreatemodel.getEmail());
        user.setPhoneNumber(userCreatemodel.getPhoneNumber());
        return user;
    }

    private Apartment createApartment(ApartmentCreateModel apartmentCreateModel) {
        Apartment apartment = new Apartment();
        apartment.setFloorNumber(apartmentCreateModel.getFloorNumber());
        apartment.setRoomNumber(apartmentCreateModel.getRoomNumber());
        apartment.setMinGuest(apartmentCreateModel.getMinGuest());
        apartment.setMaxGuest(apartmentCreateModel.getMaxGuest());
        apartment.setApartmentType(apartmentCreateModel.getApartmentType());
        apartment.setDescription(apartmentCreateModel.getDescription());
        apartment.setApartmentStatus(apartmentCreateModel.getApartmentStatus());
        apartment.setPrice(apartmentCreateModel.getPrice());
        return apartment;
    }

    private Invoice createInvoice(InvoiceCreateModel invoiceCreateModel) {
        Invoice invoice = new Invoice();
        invoice.setGenerationDate(invoiceCreateModel.getGenerationDate());
        invoice.setPaymentType(invoiceCreateModel.getPaymentType());
        invoice.setPaymentDate(invoiceCreateModel.getPaymentDate());
        return invoice;
    }
}
