package com.nebula.nebulacarapp.controller;

import com.nebula.nebulacarapp.model.Car;
import com.nebula.nebulacarapp.service.CarSevice;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @GetMapping(path = {"/admin", "/admin/{allParams}"})
    public ResponseEntity<Object> getCars(@RequestParam(required = false) @NotNull Map<String,String> allParams) {
        if (allParams != null && allParams.size() > 0) {
            String paramKey = "";
            String paramValue = "";
            for (Map.Entry<String, String> entry : allParams.entrySet()) {
                paramKey = entry.getKey();
                paramValue = entry.getValue();

            }
            return new ResponseEntity<>(carSevice.getQueriedCars(paramKey, paramValue), HttpStatus.OK) ;
        }else{
            return new ResponseEntity<>(carSevice.getAllCars(), HttpStatus.OK) ;
        }


    }

    @DeleteMapping("/admin/{carId}")
    public ResponseEntity<Object> deleteCarById(@PathVariable int carId) {
        carSevice.deleteById(carId);
        return new ResponseEntity<>( HttpStatus.valueOf(204));
    }

    @DeleteMapping("/admin/testData")
    public ResponseEntity<Object> deleteTestData(@RequestBody Map<String,String> carsToDeleteObject) {

        carSevice.deleteTestData(carsToDeleteObject);
        return new ResponseEntity<>( HttpStatus.valueOf(204));
    }


}
