package hu.webler.weblerapartmentreservation.address.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressCreateModel {

    private String country;
    private String postalCode;
    private String city;
    private String line;
}
