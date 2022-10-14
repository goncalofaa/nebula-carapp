package com.nebula.nebulacarapp.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Document(collection = "cars")
@NoArgsConstructor
@Getter
@Setter
@CompoundIndex(def = "{'brand' : 1, 'model' : 1}", unique = true)
public class Car {
    @Transient
    public static final String SEQUENCE_NAME = "users_sequence";
    @Id
    private long id;
    @NotEmpty
    private String brand;
    @NotEmpty
    private String model;
    @Min(1000)
    @Max(9999)
    @Digits(fraction = 0, integer = 4)
    @NumberFormat
    private int year;
    @NumberFormat
    private int price;
    @NumberFormat
    private int mileage;
    @NotEmpty
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
