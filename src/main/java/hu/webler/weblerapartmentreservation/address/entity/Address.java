package hu.webler.weblerapartmentreservation.address.entity;

import hu.webler.weblerapartmentreservation.apartment.entity.Apartment;
import hu.webler.weblerapartmentreservation.invoice.entity.Invoice;
import hu.webler.weblerapartmentreservation.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String country;
    private String postalCode;
    private String city;
    private String line;

    @OneToMany(mappedBy = "address")
    private List<Invoice> invoices = new ArrayList<>();

    @OneToMany(mappedBy = "address")
    private List<Apartment> apartments = new ArrayList<>();

    @OneToOne(mappedBy = "address")
    private User user;
}
