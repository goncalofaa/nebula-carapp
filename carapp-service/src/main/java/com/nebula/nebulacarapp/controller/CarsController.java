package com.nebula.nebulacarapp.controller;

import com.nebula.nebulacarapp.model.Car;
import com.nebula.nebulacarapp.service.SequenceGeneratorService;
import com.nebula.nebulacarapp.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cars")
public class CarsController {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @PostMapping(value = "/admin", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postCars(@RequestBody List<Car> cars) {
        for (Car car: cars){
            car.setId(sequenceGeneratorService.generateSequence(Car.SEQUENCE_NAME));
        }
        carRepository.saveAll(cars);

        return new ResponseEntity<String>("Database updated", HttpStatus.CREATED);
    }

    @GetMapping("/admin")
    public List<Car> getCars() {
        return carRepository.findAll();
    }

    @DeleteMapping("/admin/{carId}")
    public ResponseEntity<String> deleteCarById(@PathVariable int carId) {
        carRepository.deleteById(carId);
        return new ResponseEntity<String>("Deleted", HttpStatus.valueOf(204));
    }
}
