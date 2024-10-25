package com.cherifi.Rentacar;

import java.util.List;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
public class CarController {

    private List<Car> cars = new ArrayList<>();

    public CarController() {
        cars.add(new Car("Ferrari", "11AA22", 2020, true));
        cars.add(new Car("Tesla", "33CC44", 2021, true));
        cars.add(new Car("Ford", "33CC45", 2021, true));
    }

    @GetMapping("/cars")
    public List<Car> getCars() {
        return cars;
    }

    @GetMapping("/cars/{plateNumber}")
    public Car getCarByPlateNumber(@PathVariable String plateNumber) {
        return cars.stream()
                .filter(car -> car.getPlateNumber().equals(plateNumber))
                .findFirst()
                .orElseThrow(() -> new CarNotFoundException(plateNumber));
    }

    @PutMapping("/cars/{plateNumber}")
    public String rentOrGetBackCar(@PathVariable String plateNumber,
                                   @RequestParam(value = "rent") boolean rent,
                                   @RequestBody(required = false) Car car) {
        Car carToUpdate = cars.stream()
                .filter(c -> c.getPlateNumber().equals(plateNumber))
                .findFirst()
                .orElseThrow(() -> new CarNotFoundException(plateNumber));

        if (rent) {
            if (!carToUpdate.isAvailable()) {
                return "La voiture est déjà louée.";
            }
            carToUpdate.setAvailable(false);
            if (car != null && car.getRentalStartDate() != null && car.getRentalEndDate() != null) {
                carToUpdate.setRentalStartDate(car.getRentalStartDate());
                carToUpdate.setRentalEndDate(car.getRentalEndDate());
                return "Voiture louée avec succès du " + carToUpdate.getRentalStartDate() + " au " + carToUpdate.getRentalEndDate();
            }
            return "Voiture louée avec succès, mais aucune date de location fournie.";
        } else {
            if (carToUpdate.isAvailable()) {
                return "La voiture n'est pas louée.";
            }
            carToUpdate.setAvailable(true);
            carToUpdate.setRentalStartDate(null);
            carToUpdate.setRentalEndDate(null);
            return "Voiture retournée avec succès.";
        }
    }
}

@ResponseStatus(HttpStatus.NOT_FOUND)
class CarNotFoundException extends RuntimeException {
    public CarNotFoundException(String plateNumber) {
        super("Voiture non trouvée : " + plateNumber);
    }
}
