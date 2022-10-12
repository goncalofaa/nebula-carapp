package com.nebula.nebulacarapp.controller;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CarsControllerTest {
    @InjectMocks
    private CarsController carsController;
    @Mock
    private CarService carService;

    private static ResponseEntity<Object> response;

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
    void whenGetCarsCalledWIthParams_return200_getQueriedCarsCalled(){
        Map<String, String> params = new HashMap<>();
        params.put("body", "bodyExample");
        response = carsController.getCars(params);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(carService, times(1)).getQueriedCars("body", "bodyExample");
    }

}
