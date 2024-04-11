package hu.webler.weblerapartmentreservation.apartment.controller;

import hu.webler.weblerapartmentreservation.apartment.entity.Apartment;
import hu.webler.weblerapartmentreservation.apartment.model.ApartmentCreateModel;
import hu.webler.weblerapartmentreservation.apartment.model.ApartmentModel;
import hu.webler.weblerapartmentreservation.apartment.model.ApartmentUpdateModel;
import hu.webler.weblerapartmentreservation.apartment.service.ApartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApartmentController {

    private final ApartmentService apartmentService;

    @GetMapping("/apartments")
    public ResponseEntity<List<ApartmentModel>> renderAllApartments(Long id) {
        return ResponseEntity.status(200).body(apartmentService.findAllApartments());
    }

    @GetMapping("/apartments/{id}")
    public ResponseEntity<Apartment> findApartmentById(@PathVariable Long id) {
        return ResponseEntity.status(200).body(apartmentService.findApartmentById(id));
    }

    @PostMapping("/apartments/address/{addressId}")
    public ResponseEntity<ApartmentModel> createApartment(@RequestBody ApartmentCreateModel apartmentCreateModel, @PathVariable(name = "addressId") Long id) {
        return ResponseEntity.status(200).body(apartmentService.createApartment(apartmentCreateModel, id));
    }

    @DeleteMapping("/apartments/{id}")
    public ResponseEntity<Void> deleteApartment(@PathVariable Long id) {
        apartmentService.deleteApartment(id);
        return ResponseEntity.status(204).build();
    }

    @PatchMapping("/apartments/{id}")
    public ResponseEntity<ApartmentModel> updateApartment(@PathVariable Long id, @RequestBody ApartmentUpdateModel apartmentUpdateModel) {
        return ResponseEntity.status(200).body(apartmentService.updateApartment(id, apartmentUpdateModel));
    }
}
