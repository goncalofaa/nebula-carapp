package com.nebula.nebulacarapp.repository;

import com.nebula.nebulacarapp.model.Car;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CarRepository extends MongoRepository<Car, Integer> {
    List<Car> findByModel(String model);

    Car findByModelAndBrand(String model, String brand);
}
