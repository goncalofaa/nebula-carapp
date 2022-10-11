package com.nebula.nebulacarapp.service;

import com.nebula.nebulacarapp.model.Car;
import com.nebula.nebulacarapp.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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


}
