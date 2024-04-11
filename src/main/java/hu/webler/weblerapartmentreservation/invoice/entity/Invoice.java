package hu.webler.weblerapartmentreservation.invoice.entity;

import hu.webler.weblerapartmentreservation.address.entity.Address;
import hu.webler.weblerapartmentreservation.invoice.value.PaymentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
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
    private LocalDateTime generationDate = LocalDateTime.now();

    @Enumerated(value = EnumType.STRING)
    private PaymentType paymentType;

    private LocalDate paymentDate;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;
}
