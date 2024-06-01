package com.hei.app.kfc.model.entity;

import lombok.Data;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stock {
    private Integer id;
    private Double value;
    private Instant createAt;
    private Integer ingredientId;
    private Integer restaurantId;
}
