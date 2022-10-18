package com.nebula.nebulacarapp.service;

import com.nebula.nebulacarapp.exceptions.CustomException;
import com.nebula.nebulacarapp.exceptions.GlobalExceptionHandler;
import com.nebula.nebulacarapp.model.Car;
import com.nebula.nebulacarapp.repository.CarRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.Comparator.comparing;

@Service
public class CarService {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private CarRepository carRepository;

    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    private static Integer TryParseInt(String possibleInt) {
        try {
            return Integer.parseInt(possibleInt);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    public void saveCars(List<Car> carsList) {
        for (Car car : carsList) {
            car.setId(sequenceGeneratorService.generateSequence(Car.SEQUENCE_NAME));
            carRepository.insert(car);
        }
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }


    @SneakyThrows
    public List<Car> getQueriedCars(String paramKey, String paramValue) {
        Integer tryParseIntParamValue = TryParseInt(paramValue);
        if (!paramKey.matches("brand|model|year|price|mileage|colour")) {
            throw new CustomException("Parameters not recognized");
        } else {
            if (paramKey.matches("year|price|mileage") && tryParseIntParamValue == null) {
                throw new CustomException("Parameters not recognized");
            } else if (paramKey.matches("brand|model|colour") && tryParseIntParamValue != null) {
                throw new CustomException("Parameters not recognized");
            } else if (paramValue.contains(" ") || !paramValue.matches("^[a-zA-Z0-9_\\-.]*$")) {
                throw new CustomException("Parameters not recognized");
            } else {
                Query dynamicQuery = new Query();
                Criteria dynamicCriteria = Criteria.where(paramKey).is(tryParseIntParamValue != null ? tryParseIntParamValue : paramValue);
                dynamicQuery.addCriteria(dynamicCriteria);
                List<Car> result = mongoTemplate.find(dynamicQuery, Car.class, "cars");
                Collections.sort(result, comparing(Car::getYear).reversed());
                return result;
            }
        }


    }

    @SneakyThrows
    public void deleteById(int id) {
        if (carRepository.findById(id).isEmpty()) {
            throw new CustomException("Id not matching");
        } else {
            carRepository.deleteById(id);
        }

    }

    public void deleteTestData(Map<String, String> carsToDeleteObject) {
        String model = carsToDeleteObject.get("model");

        List<Car> carsToDelete = carRepository.findByModel(model);
        carRepository.deleteAll(carsToDelete);
    }

    @SneakyThrows
    public void updateCar(List<Car> cars) {

        for (Car car : cars) {
            if (carRepository.findByModelAndBrand(car.getModel(), car.getBrand()) == null) {
                throw new CustomException("No car matching");

            } else {

                Query dynamicQuery = new Query();
                dynamicQuery.addCriteria(new Criteria().andOperator(Criteria.where("brand").is(car.getBrand()),
                        Criteria.where("model").is(car.getModel())));


                if (car.getYear() == 0) throw new CustomException("Wrong properties for update");
                if (car.getPrice() == 0) throw new CustomException("Wrong properties for update");
                if (car.getMileage() == 0) throw new CustomException("Wrong properties for update");
                Update updateDefinition = new Update().set("year", car.getYear()).set("price", car.getPrice()).set("mileage", car.getMileage());

                mongoTemplate.findAndModify(dynamicQuery, updateDefinition, Car.class);
            }

        }

    }
}
