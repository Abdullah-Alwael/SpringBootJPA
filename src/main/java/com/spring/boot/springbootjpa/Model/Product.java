package com.spring.boot.springbootjpa.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {

    @Id
//    @NotNull(message = "id must not be empty")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "name must not be empty")
    @Size(min = 4, message = "name must not be less than 4 characters")
    @Column(columnDefinition = "Varchar(30) not null")
    private String name;

    @NotNull(message = "price must not be empty")
    @Positive(message = "price must be a positive number")
    @Column(columnDefinition = "double not null")
    private Double price;

    @NotNull(message = "categoryID must not be empty")
    @Column(columnDefinition = "int not null")
    private Integer categoryID; // from the category class

    // TODO Extra product variables:
    @Column(columnDefinition = "int")
    private Integer timesPurchased; // how many times the product was purchased

    @Column(columnDefinition = "double")
    private Double score; // used by admin for advertisements
}
