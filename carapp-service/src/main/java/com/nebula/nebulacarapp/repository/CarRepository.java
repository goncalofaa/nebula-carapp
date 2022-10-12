package com.nebula.nebulacarapp.repository;

import com.nebula.nebulacarapp.model.Car;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface CarRepository extends MongoRepository<Car, Integer> {
    @Query(value = "{ 'brand' : ?0, 'model' : ?1 }")
    List<Car> findByBrandAndModel(String brand, String model);


}
