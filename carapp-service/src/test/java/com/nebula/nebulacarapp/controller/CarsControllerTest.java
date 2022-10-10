package com.nebula.nebulacarapp.controller;

import com.nebula.nebulacarapp.model.Car;
import com.nebula.nebulacarapp.service.CarSevice;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CarsControllerTest {
    @InjectMocks
    private CarsController carsController;
    @Mock
    private CarSevice carSevice;

    private static ResponseEntity<Object> response;

    @Test
    void whenPostCarsCalled_return201_saveCarsCalled(){
        Car testCar1 = new Car("bmw", "x5",2000,50000, 15000, "black");
        List<Car> carsList = new ArrayList<>();
        carsList.add(testCar1);
        response = carsController.postCars(carsList);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(carSevice, times(1)).saveCars(carsList);
    }

    @Test
    void whenGetCarsCalled_return200_getAllCarsCalled(){
        response = carsController.getCars();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(carSevice, times(1)).getAllCars();
    }
}
