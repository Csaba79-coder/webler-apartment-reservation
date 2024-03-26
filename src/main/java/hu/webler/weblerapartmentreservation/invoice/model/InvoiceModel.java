package hu.webler.weblerapartmentreservation.invoice.model;

import hu.webler.weblerapartmentreservation.address.entity.Address;
import hu.webler.weblerapartmentreservation.apartment.entity.Apartment;
import hu.webler.weblerapartmentreservation.invoice.value.PaymentDate;
import hu.webler.weblerapartmentreservation.invoice.value.PaymentMethod;
import hu.webler.weblerapartmentreservation.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceModel {

    private Long id;
    private LocalDateTime generationDate;
    private Apartment apartment;
    private User user;
    private Address address;
    private PaymentMethod paymentMethod;
    private PaymentDate paymentDate;
}
