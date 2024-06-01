package com.hei.app.kfc.model.entity;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredientMenu {
    private Integer id;
    private Double quantity;
    private Integer menuId;
    private Integer ingredientId;
}
