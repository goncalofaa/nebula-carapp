package com.nebula.nebulacarapp.controller;

import com.nebula.nebulacarapp.model.Car;
import com.nebula.nebulacarapp.service.CarSevice;
import com.nebula.nebulacarapp.service.SequenceGeneratorService;
import com.nebula.nebulacarapp.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/cars")
public class CarsController {

    @Autowired
    private CarSevice carSevice;



    @PostMapping(value = "/admin", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> postCars(@RequestBody List<Car> cars) {
        carSevice.saveCars(cars);
        Map<String,String> responseObject = new HashMap<>();
        responseObject.put("description", "Database updated");
        return new ResponseEntity<>(responseObject, HttpStatus.CREATED);
    }

    @GetMapping("/admin")
    public ResponseEntity<Object> getCars() {
        return new ResponseEntity<>(carSevice.getAllCars(), HttpStatus.OK) ;
    }

    @DeleteMapping("/admin/{carId}")
    public ResponseEntity<Object> deleteCarById(@PathVariable int carId) {
        carSevice.deleteById(carId);
        return new ResponseEntity<>( HttpStatus.valueOf(204));
    }
}
