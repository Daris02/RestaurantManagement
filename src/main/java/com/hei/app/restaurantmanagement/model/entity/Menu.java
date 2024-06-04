package com.hei.app.restaurantmanagement.model.entity;

import lombok.Data;

import java.time.Instant;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Menu {
    private Integer id;
    private String name;
    private Double costPrice;
    private Double sellPrice;
    private Instant createAt;
    private List<IngredientMenu> ingredients;
}
