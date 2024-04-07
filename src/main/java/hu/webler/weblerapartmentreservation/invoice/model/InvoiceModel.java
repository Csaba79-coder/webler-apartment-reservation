package hu.webler.weblerapartmentreservation.invoice.model;

import hu.webler.weblerapartmentreservation.address.entity.Address;
import hu.webler.weblerapartmentreservation.invoice.value.PaymentDate;
import hu.webler.weblerapartmentreservation.invoice.value.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceModel {

    private Long id;

    @CreationTimestamp
    private LocalDateTime generationDate;
    private PaymentMethod paymentMethod;
    private PaymentDate paymentDate;
    private Address address;
}
