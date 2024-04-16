package hu.webler.weblerapartmentreservation.apartment.entity;

import hu.webler.weblerapartmentreservation.address.entity.Address;
import hu.webler.weblerapartmentreservation.apartment.value.ApartmentStatus;
import hu.webler.weblerapartmentreservation.apartment.value.ApartmentType;
import hu.webler.weblerapartmentreservation.reservation.entity.Reservation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Apartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer floorNumber;
    private Integer roomNumber;
    private Integer minGuest;
    private Integer maxGuest;

    @Enumerated(value = EnumType.STRING)
    private ApartmentType apartmentType;
    private String description;

    @Enumerated(value = EnumType.STRING)
    private ApartmentStatus apartmentStatus = ApartmentStatus.AVAILABLE;
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @OneToMany(mappedBy = "apartment")
    private List<Reservation> reservations;
}
