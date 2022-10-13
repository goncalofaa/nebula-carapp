package com.nebula.nebulacarapp.controller;

import com.mongodb.MongoWriteException;
import com.nebula.nebulacarapp.exceptions.GlobalExceptionHandler;
import com.nebula.nebulacarapp.model.Car;
import com.nebula.nebulacarapp.service.CarService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CarsControllerTest {

    @Mock
    private CarService carService;
    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @InjectMocks
    private CarsController carsController;

    ResponseEntity<?> response;

    @Test
    void whenPostCarsCalled_return201_saveCarsCalled(){
        Car testCar1 = new Car("bmw", "x5",2000,50000, 15000, "black");
        List<Car> carsList = new ArrayList<>();
        carsList.add(testCar1);
        response = carsController.postCars(carsList);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(carService, times(1)).saveCars(carsList);
    }

    @Test
    void whenPostCarsCalledWithWrongData_return400_genericException() {
        try{
            List<Car> carsList = new ArrayList<>();
            Car testCar1 = new Car(null,null,1,1,1,"1");
            carsList.add(testCar1);
            response = carsController.postCars(carsList);
            verify(carService, times(1)).saveCars(carsList);
        }
        catch(ConstraintViolationException ex){
            Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            verify(globalExceptionHandler, times(1)).genericException(ex);
        }

    }

    @Test
    void whenPostCarsCalledWithDuplicateCars_return409_duplicateKeyExceptionCalled() {
        try{
            List<Car> carsList = new ArrayList<>();
            Car testCar1 = new Car("1","1",1,1,1,"1");

            carsList.add(testCar1);

            response = carsController.postCars(carsList);
            Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
            response = carsController.postCars(carsList);
            verify(carService, times(2)).saveCars(carsList);
        }
        catch(MongoWriteException ex) {
            Assertions.assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
            verify(globalExceptionHandler, times(1)).duplicateKeyException(ex);
        }
    }

    @Test
    void whenGetCarsCalled_return200_getAllCarsCalled(){
        response = carsController.getCars(null);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(carService, times(1)).getAllCars();
    }

    @Test
    void whenDeleteCarByIdCalled_return204_deleteByIdCalled(){
        response = carsController.deleteCarById(1);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(carService, times(1)).deleteById(1);
    }

    @Test
    void whenDeleteNotAllowedCalled_return404(){
        response = carsController.deleteNotAllowed();
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }

    @Test
    void whenGetCarsCalledWIthParams_return200_getQueriedCarsCalled(){
        Map<String, String> params = new HashMap<>();
        params.put("body", "bodyExample");
        response = carsController.getCars(params);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(carService, times(1)).getQueriedCars("body", "bodyExample");
    }

    @Test
    void whenUpdateCarCalled_return200_serviceUpdateCarCalled(){

        Car testCar1 = new Car("bmw", "x5",2000,50000, 15000, "black");
        List<Car> carsList = new ArrayList<>();
        carsList.add(testCar1);
        response = carsController.postCars(carsList);
        carsList.get(0).setPrice(1000);
        response = carsController.updateCar(carsList);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(carService, times(1)).saveCars(carsList);
        verify(carService, times(1)).updateCar(carsList);
    }



}
