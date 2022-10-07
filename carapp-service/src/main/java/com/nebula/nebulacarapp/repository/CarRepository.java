package com.nebula.nebulacarapp.repository;

import com.nebula.nebulacarapp.model.Car;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CarRepository extends MongoRepository<Car, Integer> {
}
