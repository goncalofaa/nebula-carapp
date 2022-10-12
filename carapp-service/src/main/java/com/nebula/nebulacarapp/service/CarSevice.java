package com.nebula.nebulacarapp.service;

import com.nebula.nebulacarapp.model.Car;
import com.nebula.nebulacarapp.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CarSevice {
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
