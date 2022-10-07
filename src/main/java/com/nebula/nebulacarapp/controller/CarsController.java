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

    @GetMapping("/admin/{carId}")
    public Car getCarById(@PathVariable String carId) {
        return carRepository.findById(Integer.valueOf(carId)).orElse(null);
    }
}
