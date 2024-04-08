package hu.webler.weblerapartmentreservation.address.controller;

import hu.webler.weblerapartmentreservation.address.entity.Address;
import hu.webler.weblerapartmentreservation.address.model.AddressCreateModel;
import hu.webler.weblerapartmentreservation.address.model.AddressModel;
import hu.webler.weblerapartmentreservation.address.model.AddressUpdateModel;
import hu.webler.weblerapartmentreservation.address.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @GetMapping("/address")
    public ResponseEntity<List<AddressModel>> renderAllAddress() {
        return ResponseEntity.status(200).body(addressService.findAllAddress());
    }

    @GetMapping("/address/{id}")
    public ResponseEntity<Address> findAddressById(@PathVariable Long id) {
        return ResponseEntity.status(200).body(addressService.findAddressById(id));
    }

    @PostMapping("/address")
    public ResponseEntity<AddressModel> createAddress(@RequestBody AddressCreateModel addressCreateModel) {
        return ResponseEntity.status(200).body(addressService.createAddress(addressCreateModel));
    }

    @DeleteMapping("/address/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return ResponseEntity.status(204).build();
    }

    @PatchMapping("/address/{id}")
    public ResponseEntity<AddressModel> updateAddress(@PathVariable Long id, @RequestBody AddressUpdateModel addressUpdateModel) {
        return ResponseEntity.status(200).body(addressService.updateAddress(id, addressUpdateModel));
    }
}
