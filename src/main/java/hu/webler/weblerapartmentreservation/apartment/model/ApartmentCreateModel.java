package hu.webler.weblerapartmentreservation.apartment.model;

import hu.webler.weblerapartmentreservation.apartment.value.ApartmentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApartmentCreateModel {

    private Integer floorNumber;
    private Integer roomNumber;
    private Integer minGuest;
    private Integer maxGuest;
    private ApartmentType apartmentType;
    private String description;
    private BigDecimal price;
}
