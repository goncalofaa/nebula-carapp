package com.nebula.nebulacarapp.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cars")
@NoArgsConstructor
@Getter
@Setter
public class Car {
    @Transient
    public static final String SEQUENCE_NAME = "users_sequence";
    @Id
    private long id;
    private String brand;
    private String model;
    private int year;
    private int price;
    private int mileage;
    private String colour;

    public Car(String brand, String model, int year, int price, int mileage, String colour) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
        this.mileage = mileage;
        this.colour = colour;
    }



    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", price=" + price +
                ", mileage=" + mileage +
                ", colour='" + colour + '\'' +
                '}';
    }

}
