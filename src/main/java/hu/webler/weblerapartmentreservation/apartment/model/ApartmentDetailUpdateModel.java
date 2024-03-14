package hu.webler.weblerapartmentreservation.apartment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApartmentDetailUpdateModel {

    private Long id;
    private String beds;
    private String rooms;
    private int minGuest;
    private int maxGuest;
}
