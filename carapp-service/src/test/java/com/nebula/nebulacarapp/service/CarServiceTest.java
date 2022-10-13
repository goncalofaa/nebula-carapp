package com.nebula.nebulacarapp.service;


import com.nebula.nebulacarapp.exceptions.CustomException;
import com.nebula.nebulacarapp.exceptions.GlobalExceptionHandler;
import com.nebula.nebulacarapp.model.Car;
import com.nebula.nebulacarapp.repository.CarRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarServiceTest {

    @InjectMocks
    private CarService carService;

    @Mock
    private CarRepository carRepository;

    @Mock
    private SequenceGeneratorService sequenceGeneratorService;

    @Mock
    private MongoTemplate mongoTemplate;

    @Test
    void whenSaveCarsCalled_executeCarRepositoryInsert() {
        List<Car> carsList = new ArrayList<>();
        Car testCar1 = new Car("1","1",1,1,1,"1");
        carsList.add(testCar1);
        carService.saveCars(carsList);
        verify(carRepository, times(1)).insert(testCar1);

    }

    @Test
    void whenGetQueriedCars_executeMongoTemplateFind(){
        String paramKey = "brand";
        String paramValue = "exampleBody";
        carService.getQueriedCars("brand", "exampleBody");
        Query dynamicQuery = new Query();
        Criteria dynamicCriteria = Criteria.where(paramKey).is( paramValue);
        dynamicQuery.addCriteria(dynamicCriteria);
        verify(mongoTemplate, times(1)).find(dynamicQuery, Car.class, "cars");

    }

    @Test
    void whenGetQueriedCarsWithIncorrectParams_throwException(){

        assertThatThrownBy(() -> carService.getQueriedCars("notAllowedParamKey", "exampleBody"))
                .isInstanceOf(CustomException.class)
                .hasMessage("Parameters not recognized");

        assertThatThrownBy(() -> carService.getQueriedCars("brand", "1"))
                .isInstanceOf(CustomException.class)
                .hasMessage("Parameters not recognized");

        assertThatThrownBy(() ->  carService.getQueriedCars("year", "twothousand"))
                .isInstanceOf(CustomException.class)
                .hasMessage("Parameters not recognized");

        assertThatThrownBy(() -> carService.getQueriedCars("notAllowedParamKey", "exampleBody"))
                .isInstanceOf(CustomException.class)
                .hasMessage("Parameters not recognized");

    }

    @Test
    void whenDeleteByIdWIthWrongIdCalled_throwException(){

        assertThatThrownBy(() -> carService.deleteById(111111))
                .isInstanceOf(CustomException.class)
                .hasMessage("Id not matching");

    }
}
