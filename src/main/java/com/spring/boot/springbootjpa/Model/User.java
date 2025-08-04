package com.spring.boot.springbootjpa.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id
//    @NotNull(message = "id must not be empty")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "userName must not be empty")
    @Size(min = 6, message = "userName must not be less than 6 characters")
    @Column(columnDefinition = "varchar(30) not null")
    private String userName;

    @NotEmpty(message = "password must not be empty")
    @Size(min = 7, message = "password must not be less than 7 characters")
    @Pattern(regexp = "^([A-Za-z\\d])+$") // only characters and numbers, no special characters will be allowed
    @Column(columnDefinition = "varchar(30) not null")
    private String password;

    @NotEmpty(message = "email must not be empty")
    @Email(message = "email must be a valid email")
    @Column(columnDefinition = "varchar(30) not null")
    private String email;

    @NotEmpty(message = "role must not be empty")
    @Pattern(regexp = "^(Admin|Customer)$", message = "role must be either Admin or Customer")
    @Column(columnDefinition = "varchar(30) not null")
    private String role;

    @NotNull(message = "balance must not be empty")
    @Positive(message = "balance must be a positive number")
    @Column(columnDefinition = "double not null")
    private Double balance;

    // TODO Extra user variables:
    @Column(columnDefinition = "varchar(30) not null default ''")
    private String orderHistory;

    @Column(columnDefinition = "varchar(30) not null default ''")
    private String favoriteCategory;
}
