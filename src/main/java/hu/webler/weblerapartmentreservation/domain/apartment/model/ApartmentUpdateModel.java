package hu.webler.weblerapartmentreservation.domain.apartment.model;

import hu.webler.weblerapartmentreservation.domain.apartment.value.ApartmentStatus;
import hu.webler.weblerapartmentreservation.domain.apartment.value.ApartmentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApartmentUpdateModel {

    private Integer floorNumber;
    private Integer roomNumber;
    private Integer minGuest;
    private Integer maxGuest;
    private ApartmentType apartmentType;
    private String description;
    private ApartmentStatus apartmentStatus;
    private BigDecimal price;
}
