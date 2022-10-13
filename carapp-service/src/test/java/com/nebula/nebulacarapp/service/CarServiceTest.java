package com.nebula.nebulacarapp.service;


import com.nebula.nebulacarapp.model.Car;
import com.nebula.nebulacarapp.repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarServiceTest {

    @InjectMocks
    private CarService carService;

    @Mock
    private CarRepository carRepository;

    @Mock
    private MongoTemplate mongoTemplate;

    @Test
    void whenSaveCarsCalled_executeCarRepositoryInsert() {
        List<Car> carsList = new ArrayList<>();
        Car testCar1 = new Car("1","1",1,1,1,"1");
        carsList.add(testCar1);
        carService.saveCars(carsList);
        verify(carRepository, times(1)).saveAll(carsList);

    }

    @Test
    void whenGetQueriedCars_executeMongoTemplateFind(){
        String paramKey = "body";
        String paramValue = "exampleBody";
        carService.getQueriedCars("body", "exampleBody");
        Query dynamicQuery = new Query();
        Criteria dynamicCriteria = Criteria.where(paramKey).is( paramValue);
        dynamicQuery.addCriteria(dynamicCriteria);
        verify(mongoTemplate, times(1)).find(dynamicQuery, Car.class, "cars");

    }
}
