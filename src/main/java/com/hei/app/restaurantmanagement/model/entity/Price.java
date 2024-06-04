package com.hei.app.restaurantmanagement.model.entity;

import lombok.Data;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Price {
    private Integer id;
    private Double value;
    private Instant createAt;
    
    public Price(Double value) {
        this.value = value;
    }
}
