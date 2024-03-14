package hu.webler.weblerapartmentreservation.apartment.entity;

import hu.webler.weblerapartmentreservation.apartment.value.ApartmentStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApartmentDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String beds;
    private String rooms;
    private int minGuest;
    private int maxGuest;
    ApartmentStatus apartmentStatus;
}
