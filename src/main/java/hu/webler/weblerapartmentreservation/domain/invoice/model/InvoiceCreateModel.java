package hu.webler.weblerapartmentreservation.domain.invoice.model;

import hu.webler.weblerapartmentreservation.domain.address.entity.Address;

import hu.webler.weblerapartmentreservation.domain.invoice.value.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceCreateModel {

    private LocalDateTime generationDate;
    private PaymentType paymentType;
    private LocalDate paymentDate;
    private Address address;
}
