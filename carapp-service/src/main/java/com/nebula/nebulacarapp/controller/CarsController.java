package com.nebula.nebulacarapp.controller;

import com.nebula.nebulacarapp.exceptions.CustomException;
import com.nebula.nebulacarapp.model.Car;
import com.nebula.nebulacarapp.service.CarService;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cars")
@Validated
public class CarsController {

    @Autowired
    private CarService carService;




    @PostMapping(value = "/admin", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> postCars(@NotNull @NotEmpty @RequestBody List<@Valid Car> cars) {
        carService.saveCars(cars);
        Map<String,String> responseObject = new HashMap<>();
        responseObject.put("description", "Database updated");
        return new ResponseEntity<>(responseObject, HttpStatus.CREATED);
    }

    @SneakyThrows
    @GetMapping(path = {"/admin", "/admin/{allParams}"})
    public ResponseEntity<Object> getCars(@RequestParam(required = false) @NotNull Map<String,String> allParams) {
        if (allParams != null && allParams.size() > 0) {
            if(allParams.size() > 1){
                throw new CustomException("Extra parameters are present");
            }else{
                String paramKey = "";
                String paramValue = "";
                for (Map.Entry<String, String> entry : allParams.entrySet()) {
                    paramKey = entry.getKey();
                    paramValue = entry.getValue();

                }
                return new ResponseEntity<>(carService.getQueriedCars(paramKey, paramValue), HttpStatus.OK) ;
            }

        }else{
            return new ResponseEntity<>(carService.getAllCars(), HttpStatus.OK) ;
        }


    }

    @DeleteMapping("/admin/{carId}")
    public ResponseEntity<Object> deleteCarById(@PathVariable(required = false) int carId) {


        carService.deleteById(carId);
        return new ResponseEntity<>( HttpStatus.valueOf(204));
    }
    @DeleteMapping("/admin")
    public ResponseEntity<Object> deleteNotAllowed() {
        Map<String,String> responseObject = new HashMap<>();
        responseObject.put("description", "Incorrect id provided");

        return new ResponseEntity<>(responseObject, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/admin/testData")
    public ResponseEntity<Object> deleteTestData(@RequestBody Map<String,String> carsToDeleteObject) {

        carService.deleteTestData(carsToDeleteObject);
        return new ResponseEntity<>( HttpStatus.valueOf(204));
    }

    @PutMapping("/admin")
    public ResponseEntity<Object> updateCar(@RequestBody  List<@Valid Car> cars) {

        carService.updateCar(cars);

        Map<String,String> responseObject = new HashMap<>();
        responseObject.put("description", "Car Updated");
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }



}
