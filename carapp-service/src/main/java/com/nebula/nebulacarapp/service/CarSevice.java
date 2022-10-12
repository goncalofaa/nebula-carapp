package com.nebula.nebulacarapp.service;

import com.nebula.nebulacarapp.model.Car;
import com.nebula.nebulacarapp.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CarSevice {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    public void saveCars(List<Car> carsList){
        for (Car car: carsList){
            car.setId(sequenceGeneratorService.generateSequence(Car.SEQUENCE_NAME));
        }
        carRepository.saveAll(carsList);
    }

    public List<Car> getAllCars(){
        return carRepository.findAll();
    }

    private static Integer TryParseInt(String possibleInt) {
        try {
            return Integer.parseInt(possibleInt);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    public List<Car> getQueriedCars(String paramKey, String paramValue) {
        Integer tryParseIntParamValue = TryParseInt(paramValue);
        Query dynamicQuery = new Query();
        Criteria dynamicCriteria = Criteria.where(paramKey).is(tryParseIntParamValue != null ? tryParseIntParamValue : paramValue);
        dynamicQuery.addCriteria(dynamicCriteria);
        List<Car> result = mongoTemplate.find(dynamicQuery, Car.class, "cars");
        return result;
    }

    public void deleteById(int id){
        carRepository.deleteById(id);
    }

    public void deleteTestData(Map<String,String> carsToDeleteObject) {
        String model = carsToDeleteObject.get("model");
        String brand = carsToDeleteObject.get("brand");
        List<Car> carsToDelete = carRepository.findByBrandAndModel(brand, model);
        carRepository.deleteAll(carsToDelete);
    }


}
