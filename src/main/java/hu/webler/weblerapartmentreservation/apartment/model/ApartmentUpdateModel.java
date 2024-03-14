package hu.webler.weblerapartmentreservation.apartment.model;

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

    private Long id;
    private String title;
    private String description;
    private BigDecimal price;
    private int roomNumber;
    private int floorNumber;
}
