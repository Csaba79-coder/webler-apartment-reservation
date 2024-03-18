package hu.webler.weblerapartmentreservation.apartment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateModel {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
}
