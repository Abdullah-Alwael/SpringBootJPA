package com.spring.boot.springbootjpa.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MerchantStock {

    @Id
    @NotNull(message = "id must not be empty")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "productID must not be empty")
    @Column(columnDefinition = "int not null")
    private Integer productID;

    @NotNull(message = "merchantID must not be empty")
    @Column(columnDefinition = "int not null")
    private Integer merchantID;

    @NotNull(message = "stock must not be empty")
    @Min(value = 11, message = "stock must not be less than 11 initially")
    @Column(columnDefinition = "int not null")
    private Integer stock;

}
