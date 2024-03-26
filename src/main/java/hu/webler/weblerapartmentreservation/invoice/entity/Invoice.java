package hu.webler.weblerapartmentreservation.invoice.entity;

import hu.webler.weblerapartmentreservation.address.entity.Address;
import hu.webler.weblerapartmentreservation.apartment.entity.Apartment;
import hu.webler.weblerapartmentreservation.invoice.value.PaymentDate;
import hu.webler.weblerapartmentreservation.invoice.value.PaymentMethod;
import hu.webler.weblerapartmentreservation.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime generationDate;

    private Apartment apartment;
    private User user;
    private Address address;

    @Enumerated(value = EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(value = EnumType.STRING)
    private PaymentDate paymentDate;
}
